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

import com.xemantic.kotlin.js.dom.NodeBuilder
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLFieldSetElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLOptGroupElement
import org.w3c.dom.HTMLOptionElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.HTMLTextAreaElement

public inline var NodeBuilder<HTMLInputElement>.enabled: Boolean
    get() = node.enabled
    set(value) { node.enabled = value }

public inline var HTMLInputElement.enabled: Boolean
    get() = !disabled
    set(value) { disabled = !value }

public inline var NodeBuilder<HTMLButtonElement>.enabled: Boolean
    get() = node.enabled
    set(value) { node.enabled = value }

public inline var HTMLButtonElement.enabled: Boolean
    get() = !disabled
    set(value) { disabled = !value }

public inline var NodeBuilder<HTMLSelectElement>.enabled: Boolean
    get() = node.enabled
    set(value) { node.enabled = value }

public inline var HTMLSelectElement.enabled: Boolean
    get() = !disabled
    set(value) { disabled = !value }

public inline var NodeBuilder<HTMLTextAreaElement>.enabled: Boolean
    get() = node.enabled
    set(value) { node.enabled = value }

public inline var HTMLTextAreaElement.enabled: Boolean
    get() = !disabled
    set(value) { disabled = !value }

public inline var NodeBuilder<HTMLFieldSetElement>.enabled: Boolean
    get() = node.enabled
    set(value) { node.enabled = value }

public inline var HTMLFieldSetElement.enabled: Boolean
    get() = !disabled
    set(value) { disabled = !value }

public inline var NodeBuilder<HTMLOptionElement>.enabled: Boolean
    get() = node.enabled
    set(value) { node.enabled = value }

public inline var HTMLOptionElement.enabled: Boolean
    get() = !disabled
    set(value) { disabled = !value }

public inline var NodeBuilder<HTMLOptGroupElement>.enabled: Boolean
    get() = node.enabled
    set(value) { node.enabled = value }

public inline var HTMLOptGroupElement.enabled: Boolean
    get() = !disabled
    set(value) { disabled = !value }