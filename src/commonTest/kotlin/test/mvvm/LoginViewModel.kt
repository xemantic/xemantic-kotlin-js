/*
 * Copyright 2026 Kazimierz Pogoda / Xemantic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xemantic.kotlin.js.test.mvvm

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel for the Login view, managing authentication state and user interactions.
 *
 * Exposes reactive [submitEnabled], [error], and [loading] state flows for the view to observe.
 * All dependencies are injected via the constructor, keeping this class platform-independent
 * and testable without a browser environment.
 *
 * The [dispatcher] is injected to decouple the ViewModel from any specific threading model:
 *
 * - **Kotlin/JS and Kotlin/WASM** — JavaScript is single-threaded, so `Dispatchers.Default`
 *   and `Dispatchers.Main` are the same event-loop dispatcher. Either can be passed here.
 * - **Android** — `Dispatchers.Main` (or `Dispatchers.Main.immediate`) should be used so that
 *   `StateFlow` updates are emitted on the UI thread and can safely drive view updates.
 * - **iOS (Kotlin/Native)** — `Dispatchers.Main` maps to the main dispatch queue and should
 *   be used for the same reason as on Android.
 * - **Tests** — an `UnconfinedTestDispatcher` is passed to execute coroutines eagerly and
 *   deterministically, without needing a real event loop or UI thread.
 *
 * @param dispatcher the [CoroutineDispatcher] used to create the ViewModel's [scope].
 * @param authenticator the [Authenticator] service handling credential verification.
 * @param navigator the [Navigator] service handling screen transitions after successful login.
 */
class LoginViewModel(
    dispatcher: CoroutineDispatcher,
    private val authenticator: Authenticator,
    private val navigator: Navigator
) {

    /**
     * Current form field values, kept private as internal state.
     * Unlike [submitEnabled], [error], and [loading], these are not exposed as `StateFlow`s
     * because the view already holds the source of truth (the input elements) — the ViewModel
     * only needs to remember the latest values for [onSubmit].
     */
    private var username: String = ""
    private var password: String = ""

    /**
     * The coroutine scope tied to this ViewModel's lifecycle.
     *
     * Uses [SupervisorJob] so that a failure in one child coroutine (e.g., a network
     * error during authentication) does not cancel the entire scope and all other
     * coroutines — such as the `StateFlow` collectors driving UI updates in the view.
     *
     * Must be cancelled by calling [onCleared] when the ViewModel is no longer needed.
     *
     * Note: the scope is public so that the view can collect `StateFlow`s using
     * `launchIn(viewModel.scope)`, tying UI subscriptions to the ViewModel's lifecycle.
     */
    val scope = CoroutineScope(dispatcher + SupervisorJob())

    /**
     * Observable UI state exposed as [StateFlow]s.
     *
     * A `StateFlow` is a reactive primitive from `kotlinx.coroutines` that always holds
     * a current [value][StateFlow.value] and emits updates to all active collectors.
     * It is the Kotlin equivalent of Android's `LiveData`, RxJava's `BehaviorSubject`,
     * or a JavaScript framework's reactive signal/store.
     *
     * The backing fields use [MutableStateFlow], which allows the ViewModel to update
     * values internally, while the public type is the read-only [StateFlow] — ensuring
     * that only the ViewModel can mutate state, and the view can only observe it.
     *
     * The view subscribes to these flows (e.g., `viewModel.submitEnabled.onEach { ... }`)
     * and reacts to state changes by updating DOM properties like `disabled` or `hidden`.
     */
    val submitEnabled: StateFlow<Boolean>
        field = MutableStateFlow(false)

    val error: StateFlow<String?>
        field = MutableStateFlow<String?>(null)

    val loading: StateFlow<Boolean>
        field = MutableStateFlow(false)

    fun onUsernameChanged(username: String) {
        this.username = username
        error.value = null
        updateSubmitEnabled()
    }

    fun onPasswordChanged(password: String) {
        this.password = password
        error.value = null
        updateSubmitEnabled()
    }

    fun onSubmit() {
        if (!submitEnabled.value) return
        submitEnabled.value = false
        loading.value = true
        scope.launch {
            try {
                // The authenticator implementation may internally switch to Dispatchers.IO
                // (or another background dispatcher) to perform the network request on
                // platforms like Android or JVM. On Kotlin/JS this is irrelevant, as all
                // dispatchers share the single event-loop thread. Either way, the suspend
                // function returns on the caller's dispatcher, so the StateFlow updates
                // below always run on the ViewModel's scope dispatcher.
                if (authenticator.authenticate(username, password)) {
                    navigator.goTo("Home")
                } else {
                    error.value = "Invalid credentials"
                    submitEnabled.value = true
                }
            } catch (e: Exception) {
                error.value = "Something went wrong: ${e.message}"
                submitEnabled.value = true
            } finally {
                loading.value = false
            }
        }
    }

    fun onCleared() {
        scope.cancel()
    }

    private fun updateSubmitEnabled() {
        submitEnabled.value = username.isNotEmpty() && password.isNotEmpty()
    }

}