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

import com.xemantic.kotlin.js.dom.aria.aria
import com.xemantic.kotlin.js.dom.aria.label
import com.xemantic.kotlin.js.dom.aria.role
import com.xemantic.kotlin.js.dom.html.*
import com.xemantic.kotlin.test.assert
import com.xemantic.kotlin.test.coroutines.should
import com.xemantic.kotlin.test.have
import com.xemantic.kotlin.test.sameAsHtml
import com.xemantic.markanywhere.js.toSemanticEvents
import com.xemantic.markanywhere.render.render
import kotlinx.browser.document
import kotlinx.coroutines.test.runTest
import org.w3c.dom.Element
import org.w3c.dom.Text
import org.w3c.dom.get
import kotlin.test.Test

class DomDslTest {

    @Test
    fun `should create HTML element`() = runTest {
        // when
        val html = nodes.html(lang = "en") {
            body {
                div("foo") {
                    button("large") {
                        aria.label = "Hello World"
                        dataset["myTest"] = "hello-button"
                        +"Hello World"
                    }
                    a(href = "https://example.com")
                    "my:component"("bar", id = "component-1") {
                        div("foo")
                    }
                }
            }
        }

        // then
        html.toSemanticEvents().render() sameAsHtml """
            <html lang="en">
              <body>
                <div class="foo">
                  <button class="large" aria-label="Hello World" data-my-test="hello-button">Hello World</button><a href="https://example.com"></a>
                  <my:component class="bar" id="component-1">
                    <div class="foo">
                    </div>
                  </my:component>
                </div>
              </body>
            </html>
        """.trimIndent()
    }

    @Test
    fun `should create login form`() = runTest {
        // when
        val form = nodes.form("app-login") {
            aria.label = "Login"

            div("field label border round prefix") {
                icon("mail")
                input("large border", name = "username", type = "text") {
                    aria.label = "Username"
                }
                label { +"Username" }
            }

            div("field label border round prefix") {
                icon("key")
                input("large border", name = "password", type = "password") {
                    aria.label = "Password"
                }
                label { +"Password" }
            }

            nav("no-space") {
                button("large", type = "submit") {
                    aria.label = "Submit"
                    +"Submit"
                }
            }

            progress("circle") {
                role = "progressbar"
                aria.label = "Loading"
                hidden = true
            }

            div("snackbar") {
                role = "alert"
                icon("warning")
            }

        }

        // then
        form.toSemanticEvents().render() sameAsHtml """
            <form class="app-login" aria-label="Login">
              <div class="field label border round prefix">
                <i aria-hidden="true">mail</i><input class="large border" name="username" type="text" aria-label="Username"/><label>Username</label>
              </div>
              <div class="field label border round prefix">
                <i aria-hidden="true">key</i><input class="large border" name="password" type="password" aria-label="Password"/><label>Password</label>
              </div>
              <nav class="no-space">
                <button class="large" type="submit" aria-label="Submit">Submit</button>
              </nav>
              <progress class="circle" role="progressbar" aria-label="Loading" hidden=""></progress>
              <div class="snackbar" role="alert">
                <i aria-hidden="true">warning</i>
              </div>
            </form>
        """.trimIndent()
    }

    @Test
    fun `should apply block to icon element`() = runTest {
        // when
        val icon = nodes.icon("star", klass = "small") {
            dataset["testId"] = "star-icon"
            aria.label = "favorite"
        }

        // then
        icon.toSemanticEvents().render() sameAsHtml """
            <i class="small" aria-hidden="true" data-test-id="star-icon" aria-label="favorite">star</i>
        """.trimIndent()
    }

    @Test
    fun `should replace contents of existing HTML element when invoked`() = runTest {
        // given
        val body = nodes.body {
            p { +"original" }
        }

        // when
        body {
            div("foo") {
                +"Hello World"
            }
        }

        // then
        body.toSemanticEvents().render() sameAsHtml """
            <body>
              <div class="foo">
                Hello World
              </div>
            </body>
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
    fun `should set and get data attributes`() = runTest {
        // when
        val div = nodes.div {
            dataset["foo"] = "bar"
            dataset["otherAttribute"] = "value2"
        }

        // then
        div.toSemanticEvents().render() sameAsHtml """
          <div data-foo="bar" data-other-attribute="value2">
          </div>
        """.trimIndent()
    }

    @Test
    fun `should return null for non-existent data attribute`() = runTest {
        nodes.div {
            assert(dataset["nonexistent"] == null)
        }
    }

    @Test
    fun `should clear data attribute when set to null`() = runTest {
        val element = nodes.div {
            dataset["test"] = "hello"
            dataset["test"] = null
        }
        assert(element.dataset["test"] == null)
    }

    @Test
    fun `should clear camelCase data attribute when set to null`() = runTest {
        val element = nodes.div {
            dataset["testValue"] = "hello"
            dataset["testValue"] = null
        }
        assert(element.dataset["testValue"] == null)
    }

    @Test
    fun `should set and get hidden property`() {
        // given
        val element = nodes.div {
            hidden = true
        }
        assert(element.hidden)

        // when
        element + {
            hidden = false
        }
        
        // then
        assert(!element.hidden)
    }

    @Test
    fun `should set native properties via attributes block`() = runTest {
        // when
        val input = nodes.input(type = "text") {
            attributes {
                required = true
                maxLength = 20
            }
        }

        // then
        input should {
            have(required)
            have(maxLength == 20)
        }
    }

    @Test
    fun `should create a single Text node when multiple strings are added`() = runTest {
        // when
        val element = nodes.div {
            +"foo"
            +"bar"
            +"baz"
        }

        // then
        element should {
            have(childNodes.length == 1)
            have(firstChild is Text)
            have(element.textContent == "foobarbaz")
        }
    }

    @Test
    fun `should replace contents when NodeBuilder is invoked`() = runTest {
        // given
        val builder = nodes
        builder.div("stale")

        // when
        builder {
            div("fresh") {
                +"Hello World"
            }
        }

        // then
        builder.node should {
            have(childNodes.length == 1)
        }
        (builder.node.firstChild as Element).toSemanticEvents().render() sameAsHtml """
            <div class="fresh">
              Hello World
            </div>
        """.trimIndent()
    }

    @Test
    fun `should accumulate children when NodeBuilder is plused multiple times`() = runTest {
        // given
        val builder = nodes
        builder.div("first")

        // when
        builder + {
            div("second")
        }
        builder + {
            div("third")
        }

        // then
        builder.node should {
            have(childNodes.length == 3)
            have((childNodes[0] as Element).className == "first")
            have((childNodes[1] as Element).className == "second")
            have((childNodes[2] as Element).className == "third")
        }
    }

    @Test
    fun `should add nodes to existing element without clearing`() = runTest {
        // given
        val article = nodes.article {
            h1 { +"Title" }
        }

        // when
        article + {
            section {
                +"content"
            }
        }

        // then
        article.toSemanticEvents().render() sameAsHtml """
            <article>
              <h1>
                Title
              </h1>
              <section>
                content
              </section>
            </article>
        """.trimIndent()
    }

}
