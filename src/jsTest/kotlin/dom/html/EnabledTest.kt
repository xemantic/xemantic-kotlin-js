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

package com.xemantic.kotlin.js.dom.html

import com.xemantic.kotlin.js.dom.nodes
import com.xemantic.kotlin.test.assert
import org.w3c.dom.HTMLOptGroupElement
import org.w3c.dom.HTMLOptionElement
import kotlin.test.Test

class EnabledTest {

    @Test
    fun `should default input to enabled`() {
        // given
        val element = nodes.input()

        // then
        assert(!element.disabled)
        assert(element.enabled)
    }

    @Test
    fun `should disable input via enabled setter`() {
        // given
        val element = nodes.input {
            enabled = false
        }

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

    @Test
    fun `should disable input via direct enabled setter`() {
        // given
        val element = nodes.input()

        // when
        element.enabled = false

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

    @Test
    fun `should default button to enabled`() {
        // given
        val element = nodes.button()

        // then
        assert(!element.disabled)
        assert(element.enabled)
    }

    @Test
    fun `should disable button via enabled setter`() {
        // given
        val element = nodes.button {
            enabled = false
        }

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

    @Test
    fun `should disable button via direct enabled setter`() {
        // given
        val element = nodes.button()

        // when
        element.enabled = false

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

    @Test
    fun `should default select to enabled`() {
        // given
        val element = nodes.select()

        // then
        assert(!element.disabled)
        assert(element.enabled)
    }

    @Test
    fun `should disable select via enabled setter`() {
        // given
        val element = nodes.select {
            enabled = false
        }

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

    @Test
    fun `should disable select via direct enabled setter`() {
        // given
        val element = nodes.select()

        // when
        element.enabled = false

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

    @Test
    fun `should default textarea to enabled`() {
        // given
        val element = nodes.textarea()

        // then
        assert(!element.disabled)
        assert(element.enabled)
    }

    @Test
    fun `should disable textarea via enabled setter`() {
        // given
        val element = nodes.textarea {
            enabled = false
        }

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

    @Test
    fun `should disable textarea via direct enabled setter`() {
        // given
        val element = nodes.textarea()

        // when
        element.enabled = false

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

    @Test
    fun `should default fieldset to enabled`() {
        // given
        val element = nodes.fieldset()

        // then
        assert(!element.disabled)
        assert(element.enabled)
    }

    @Test
    fun `should disable fieldset via enabled setter`() {
        // given
        val element = nodes.fieldset {
            enabled = false
        }

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

    @Test
    fun `should disable fieldset via direct enabled setter`() {
        // given
        val element = nodes.fieldset()

        // when
        element.enabled = false

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

    @Test
    fun `should default option to enabled`() {
        // given
        lateinit var element: HTMLOptionElement
        nodes.select {
            element = option(value = "a")
        }

        // then
        assert(!element.disabled)
        assert(element.enabled)
    }

    @Test
    fun `should disable option via enabled setter`() {
        // given
        lateinit var element: HTMLOptionElement
        nodes.select {
            element = option(value = "a") {
                enabled = false
            }
        }

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

    @Test
    fun `should disable option via direct enabled setter`() {
        // given
        lateinit var element: HTMLOptionElement
        nodes.select {
            element = option(value = "a")
        }

        // when
        element.enabled = false

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

    @Test
    fun `should default optgroup to enabled`() {
        // given
        lateinit var element: HTMLOptGroupElement
        nodes.select {
            element = optgroup(label = "group")
        }

        // then
        assert(!element.disabled)
        assert(element.enabled)
    }

    @Test
    fun `should disable optgroup via enabled setter`() {
        // given
        lateinit var element: HTMLOptGroupElement
        nodes.select {
            element = optgroup(label = "group") {
                enabled = false
            }
        }

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

    @Test
    fun `should disable optgroup via direct enabled setter`() {
        // given
        lateinit var element: HTMLOptGroupElement
        nodes.select {
            element = optgroup(label = "group")
        }

        // when
        element.enabled = false

        // then
        assert(element.disabled)
        assert(!element.enabled)
    }

}