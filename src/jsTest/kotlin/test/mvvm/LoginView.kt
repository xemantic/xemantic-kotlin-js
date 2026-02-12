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

import com.xemantic.kotlin.js.dom.ariaLabel
import com.xemantic.kotlin.js.dom.html.*
import com.xemantic.kotlin.js.dom.node
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun loginView(
    viewModel: LoginViewModel
) = node { form("app-login") {

    it.onsubmit = { event -> event.preventDefault() }

    div("field label border round prefix") {
        icon("mail")
        input("large border", name = "username", type = "text") { input ->
            input.oninput = {
                viewModel.onUsernameChanged(input.value)
            }
        }
        label { +"Username" }
    }

    div("field label border round prefix") {
        icon("key")
        input("large border", name = "password", type = "password") { input ->
            input.oninput = {
                viewModel.onPasswordChanged(input.value)
            }
        }
        label { +"Password" }
    }

    nav("no-space") {
        button("large", type = "submit") {
            it.ariaLabel = "Submit"
            viewModel.submitEnabled.onEach { enabled ->
                it.disabled = !enabled
            }.launchIn(viewModel.scope)
            it.onclick = {
                viewModel.onSubmit()
            }
            +"Submit"
        }
    }

    progress("circle") {
        it.hidden = true
        viewModel.loading.onEach { loading ->
            it.hidden = !loading
        }.launchIn(viewModel.scope)
    }

    div("snackbar") {
        icon("warning")
        val errorSpan = span {}
        viewModel.error.onEach { error ->
            if (error != null) {
                it.classList.add("active")
                errorSpan.textContent = error
            } else {
                it.classList.remove("active")
            }
        }.launchIn(viewModel.scope)
    }

}}
