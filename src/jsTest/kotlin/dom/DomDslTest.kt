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

package com.xemantic.kotlin.js.dom

import com.xemantic.kotlin.js.dom.html.*
import com.xemantic.kotlin.test.coroutines.should
import com.xemantic.kotlin.test.have
import com.xemantic.kotlin.test.sameAsHtml
import com.xemantic.markanywhere.js.toSemanticEvents
import com.xemantic.markanywhere.render.render
import kotlinx.browser.document
import kotlinx.coroutines.test.runTest
import org.w3c.dom.Element
import org.w3c.dom.Text
import kotlin.test.Test

class DomDslTest {

    @Test
    fun `should create HTML element`() = runTest {
        // when
        val html = node { html(lang = "en") {
            body {
                div("foo") {
                    button("large") { +"Hello World" }
                    a(href = "https://example.com")
                    "my:component"("bar") {
                        it.id = "component-1"
                    }
                }
            }
        }}

        // then
        html.toSemanticEvents().render() sameAsHtml """
            <html lang="en">
              <body>
                <div class="foo">
                  <button class="large">Hello World</button><a href="https://example.com"></a>
                  <my:component class="bar" id="component-1">
                  </my:component>
                </div>
              </body>
            </html>
        """.trimIndent()
    }

    @Test
    fun `should append to existing HTML element`() = runTest {
        // given
        val body = document.body!!

        // when
        body {
            div("foo") {
                +"Hello World"
            }
        }

        // then
        (body.lastChild as Element).toSemanticEvents().render() sameAsHtml """
            <div class="foo">
              Hello World
            </div>
        """.trimIndent()
    }

    @Test
    fun `should append to newly created HTML element`() = runTest {
        // given
        val element = document.createElement("article")

        // when
        element {
            div("foo") {
                +"Hello World"
            }
        }

        // then
        element.toSemanticEvents().render() sameAsHtml """
            <article>
              <div class="foo">
                Hello World
              </div>
            </article>
        """.trimIndent()
    }

    @Test
    fun `should create a single Text node when multiple strings are added`() = runTest {
        // when
        val element = node { "div" {
            +"foo"
            +"bar"
            +"baz"
        }}

        // then
        element should {
            have(childNodes.length == 1)
            have(firstChild is Text)
            have(element.textContent == "foobarbaz")
        }
    }

}
