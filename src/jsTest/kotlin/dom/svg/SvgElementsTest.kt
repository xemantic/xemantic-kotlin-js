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

package com.xemantic.kotlin.js.dom.svg

import com.xemantic.kotlin.js.dom.element.set
import com.xemantic.kotlin.js.dom.node
import com.xemantic.kotlin.test.sameAs
import com.xemantic.markanywhere.js.toSemanticEvents
import com.xemantic.markanywhere.render.render
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SvgElementsTest {

    @Test
    fun `should create SVG element tree with all possible SVG tags`() = runTest {
        // when
        val svg = node.svg("main-image") {
            node["viewBox"] = "0 0 800 600"
            node["width"] = "800"
            node["height"] = "600"
            title { +"Landscape Scene" }
            desc { +"An SVG landscape with mountains, trees, and sky" }
            metadata { +"Created with xemantic-kotlin-js" }
            style { +".ground { fill: #567d46; }" }
            script { +"document.querySelector('svg').dataset.loaded = 'true'" }
            view(id = "detail") {
                node["viewBox"] = "200 150 400 300"
            }
            defs {
                linearGradient(id = "skyGradient") {
                    node["x1"] = "0"
                    node["y1"] = "0"
                    node["x2"] = "0"
                    node["y2"] = "1"
                    stop {
                        node["offset"] = "0%"
                        node["stop-color"] = "#87CEEB"
                    }
                    stop {
                        node["offset"] = "100%"
                        node["stop-color"] = "#E0F0FF"
                    }
                }
                radialGradient {
                    node["id"] = "sunGlow"
                    node["cx"] = "0.5"
                    node["cy"] = "0.5"
                    node["r"] = "0.5"
                    stop {
                        node["offset"] = "0%"
                        node["stop-color"] = "#FFD700"
                    }
                    stop {
                        node["offset"] = "100%"
                        node["stop-color"] = "transparent"
                    }
                }
                pattern(id = "grass") {
                    node["width"] = "10"
                    node["height"] = "10"
                    node["patternUnits"] = "userSpaceOnUse"
                    rect {
                        node["width"] = "10"
                        node["height"] = "10"
                        node["fill"] = "#567d46"
                    }
                    circle {
                        node["cx"] = "5"
                        node["cy"] = "5"
                        node["r"] = "2"
                        node["fill"] = "#4a6b3c"
                    }
                }
                marker(id = "arrowhead") {
                    node["markerWidth"] = "10"
                    node["markerHeight"] = "7"
                    node["refX"] = "10"
                    node["refY"] = "3.5"
                    node["orient"] = "auto"
                    path {
                        node["d"] = "M 0 0 L 10 3.5 L 0 7 Z"
                        node["fill"] = "#333"
                    }
                }
                clipPath(id = "sceneClip") {
                    rect {
                        node["width"] = "800"
                        node["height"] = "600"
                        node["rx"] = "20"
                    }
                }
                mask(id = "fadeMask") {
                    ellipse {
                        node["cx"] = "400"
                        node["cy"] = "300"
                        node["rx"] = "350"
                        node["ry"] = "250"
                        node["fill"] = "white"
                    }
                }
                filter(id = "shadow") {
                    feGaussianBlur {
                        node["in"] = "SourceAlpha"
                        node["stdDeviation"] = "3"
                        node["result"] = "blur"
                    }
                    feOffset {
                        node["in"] = "blur"
                        node["dx"] = "2"
                        node["dy"] = "2"
                        node["result"] = "offsetBlur"
                    }
                    feFlood {
                        node["flood-color"] = "rgba(0,0,0,0.4)"
                        node["result"] = "flood"
                    }
                    feComposite {
                        node["in"] = "flood"
                        node["in2"] = "offsetBlur"
                        node["operator"] = "in"
                        node["result"] = "shadow"
                    }
                    feBlend {
                        node["in"] = "SourceGraphic"
                        node["in2"] = "shadow"
                        node["mode"] = "normal"
                    }
                    feColorMatrix {
                        node["type"] = "saturate"
                        node["values"] = "1.2"
                    }
                    feComponentTransfer {
                        feFuncR {
                            node["type"] = "linear"
                            node["slope"] = "1.1"
                        }
                        feFuncG {
                            node["type"] = "linear"
                            node["slope"] = "1.1"
                        }
                        feFuncB {
                            node["type"] = "linear"
                            node["slope"] = "0.9"
                        }
                        feFuncA {
                            node["type"] = "identity"
                        }
                    }
                    feConvolveMatrix {
                        node["order"] = "3"
                        node["kernelMatrix"] = "0 -1 0 -1 5 -1 0 -1 0"
                    }
                    feDiffuseLighting {
                        node["surfaceScale"] = "5"
                        node["diffuseConstant"] = "1"
                        feDistantLight {
                            node["azimuth"] = "235"
                            node["elevation"] = "40"
                        }
                    }
                    feSpecularLighting {
                        node["surfaceScale"] = "5"
                        node["specularConstant"] = "0.75"
                        node["specularExponent"] = "20"
                        fePointLight {
                            node["x"] = "150"
                            node["y"] = "60"
                            node["z"] = "200"
                        }
                        feSpotLight {
                            node["x"] = "400"
                            node["y"] = "0"
                            node["z"] = "300"
                            node["pointsAtX"] = "400"
                            node["pointsAtY"] = "300"
                            node["pointsAtZ"] = "0"
                        }
                    }
                    feDisplacementMap {
                        node["in"] = "SourceGraphic"
                        node["in2"] = "turbulence"
                        node["scale"] = "10"
                    }
                    feTurbulence {
                        node["type"] = "fractalNoise"
                        node["baseFrequency"] = "0.02"
                        node["numOctaves"] = "3"
                        node["result"] = "turbulence"
                    }
                    feImage {
                        node["href"] = "texture.png"
                        node["result"] = "texture"
                    }
                    feMorphology {
                        node["operator"] = "dilate"
                        node["radius"] = "2"
                    }
                    feMerge {
                        feMergeNode {
                            node["in"] = "offsetBlur"
                        }
                        feMergeNode {
                            node["in"] = "SourceGraphic"
                        }
                    }
                    feTile {
                        node["in"] = "texture"
                    }
                }
                symbol(id = "tree") {
                    node["viewBox"] = "0 0 40 80"
                    polyline {
                        node["points"] = "20,80 20,30 10,30 20,5 30,30 20,30"
                        node["fill"] = "none"
                        node["stroke"] = "#228B22"
                        node["stroke-width"] = "2"
                    }
                }
            }
            g("scene") {
                node["clip-path"] = "url(#sceneClip)"
                rect("sky") {
                    node["width"] = "800"
                    node["height"] = "400"
                    node["fill"] = "url(#skyGradient)"
                }
                circle {
                    node["cx"] = "650"
                    node["cy"] = "100"
                    node["r"] = "50"
                    node["fill"] = "url(#sunGlow)"
                    animate {
                        node["attributeName"] = "r"
                        node["values"] = "48;52;48"
                        node["dur"] = "3s"
                        node["repeatCount"] = "indefinite"
                    }
                }
                path {
                    node["d"] = "M 0 350 Q 200 250 400 300 T 800 280 L 800 600 L 0 600 Z"
                    node["fill"] = "url(#grass)"
                }
                line {
                    node["x1"] = "0"
                    node["y1"] = "400"
                    node["x2"] = "800"
                    node["y2"] = "400"
                    node["stroke"] = "#333"
                    node["stroke-width"] = "1"
                }
                polygon {
                    node["points"] = "300,400 400,200 500,400"
                    node["fill"] = "#8B7355"
                }
                polyline {
                    node["points"] = "0,380 200,360 400,350 600,340 800,350"
                    node["fill"] = "none"
                    node["stroke"] = "#333"
                    node["stroke-width"] = "2"
                    node["marker-end"] = "url(#arrowhead)"
                }
                ellipse {
                    node["cx"] = "400"
                    node["cy"] = "420"
                    node["rx"] = "200"
                    node["ry"] = "20"
                    node["fill"] = "rgba(0,0,0,0.1)"
                }
                image {
                    node["href"] = "cloud.png"
                    node["x"] = "100"
                    node["y"] = "50"
                    node["width"] = "120"
                    node["height"] = "60"
                }
                use {
                    node["href"] = "#tree"
                    node["x"] = "150"
                    node["y"] = "300"
                    node["width"] = "40"
                    node["height"] = "80"
                }
                a(href = "https://xemantic.com", target = "_blank") {
                    text {
                        node["x"] = "400"
                        node["y"] = "560"
                        node["text-anchor"] = "middle"
                        node["font-family"] = "sans-serif"
                        node["font-size"] = "16"
                        tspan {
                            node["font-weight"] = "bold"
                            +"Landscape "
                        }
                        tspan {
                            node["fill"] = "#666"
                            +"Illustration"
                        }
                    }
                }
                text("path-label") {
                    textPath {
                        node["href"] = "#curvePath"
                        +"Text along a curved path"
                    }
                }
                foreignObject {
                    node["x"] = "620"
                    node["y"] = "460"
                    node["width"] = "160"
                    node["height"] = "80"
                }
                switch {
                    g {
                        node["requiredFeatures"] = "http://www.w3.org/TR/SVG11/feature#Shape"
                        rect {
                            node["x"] = "10"
                            node["y"] = "10"
                            node["width"] = "100"
                            node["height"] = "40"
                            node["fill"] = "#eee"
                        }
                    }
                }
            }
            animateMotion {
                node["dur"] = "5s"
                node["repeatCount"] = "indefinite"
                node["path"] = "M 0 0 L 100 0"
            }
            animateTransform {
                node["attributeName"] = "transform"
                node["type"] = "rotate"
                node["from"] = "0 400 300"
                node["to"] = "360 400 300"
                node["dur"] = "60s"
                node["repeatCount"] = "indefinite"
            }
            set {
                node["attributeName"] = "visibility"
                node["to"] = "visible"
                node["begin"] = "2s"
            }
        }

        // then
        svg.toSemanticEvents().render() sameAs /* language=html */ """
            <svg class="main-image" viewBox="0 0 800 600" width="800" height="600">
              <title>
                Landscape Scene
              </title>
              <desc>
                An SVG landscape with mountains, trees, and sky
              </desc>
              <metadata>
                Created with xemantic-kotlin-js
              </metadata>
              <style>
            .ground { fill: #567d46; }
              </style>
              <script>
            document.querySelector('svg').dataset.loaded = 'true'
              </script>
              <view id="detail" viewBox="200 150 400 300"/>
              <defs>
                <linearGradient id="skyGradient" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="0%" stop-color="#87CEEB"/>
                  <stop offset="100%" stop-color="#E0F0FF"/>
                </linearGradient>
                <radialGradient id="sunGlow" cx="0.5" cy="0.5" r="0.5">
                  <stop offset="0%" stop-color="#FFD700"/>
                  <stop offset="100%" stop-color="transparent"/>
                </radialGradient>
                <pattern id="grass" width="10" height="10" patternUnits="userSpaceOnUse">
                  <rect width="10" height="10" fill="#567d46"/>
                  <circle cx="5" cy="5" r="2" fill="#4a6b3c"/>
                </pattern>
                <marker id="arrowhead" markerWidth="10" markerHeight="7" refX="10" refY="3.5" orient="auto">
                  <path d="M 0 0 L 10 3.5 L 0 7 Z" fill="#333"/>
                </marker>
                <clipPath id="sceneClip">
                  <rect width="800" height="600" rx="20"/>
                </clipPath>
                <mask id="fadeMask">
                  <ellipse cx="400" cy="300" rx="350" ry="250" fill="white"/>
                </mask>
                <filter id="shadow">
                  <feGaussianBlur in="SourceAlpha" stdDeviation="3" result="blur"/>
                  <feOffset in="blur" dx="2" dy="2" result="offsetBlur"/>
                  <feFlood flood-color="rgba(0,0,0,0.4)" result="flood"/>
                  <feComposite in="flood" in2="offsetBlur" operator="in" result="shadow"/>
                  <feBlend in="SourceGraphic" in2="shadow" mode="normal"/>
                  <feColorMatrix type="saturate" values="1.2"/>
                  <feComponentTransfer>
                    <feFuncR type="linear" slope="1.1"/>
                    <feFuncG type="linear" slope="1.1"/>
                    <feFuncB type="linear" slope="0.9"/>
                    <feFuncA type="identity"/>
                  </feComponentTransfer>
                  <feConvolveMatrix order="3" kernelMatrix="0 -1 0 -1 5 -1 0 -1 0"/>
                  <feDiffuseLighting surfaceScale="5" diffuseConstant="1">
                    <feDistantLight azimuth="235" elevation="40"/>
                  </feDiffuseLighting>
                  <feSpecularLighting surfaceScale="5" specularConstant="0.75" specularExponent="20">
                    <fePointLight x="150" y="60" z="200"/>
                    <feSpotLight x="400" y="0" z="300" pointsAtX="400" pointsAtY="300" pointsAtZ="0"/>
                  </feSpecularLighting>
                  <feDisplacementMap in="SourceGraphic" in2="turbulence" scale="10"/>
                  <feTurbulence type="fractalNoise" baseFrequency="0.02" numOctaves="3" result="turbulence"/>
                  <feImage href="texture.png" result="texture"/>
                  <feMorphology operator="dilate" radius="2"/>
                  <feMerge>
                    <feMergeNode in="offsetBlur"/>
                    <feMergeNode in="SourceGraphic"/>
                  </feMerge>
                  <feTile in="texture"/>
                </filter>
                <symbol id="tree" viewBox="0 0 40 80">
                  <polyline points="20,80 20,30 10,30 20,5 30,30 20,30" fill="none" stroke="#228B22" stroke-width="2"/>
                </symbol>
              </defs>
              <g class="scene" clip-path="url(#sceneClip)">
                <rect class="sky" width="800" height="400" fill="url(#skyGradient)"/>
                <circle cx="650" cy="100" r="50" fill="url(#sunGlow)">
                  <animate attributeName="r" values="48;52;48" dur="3s" repeatCount="indefinite"/>
                </circle>
                <path d="M 0 350 Q 200 250 400 300 T 800 280 L 800 600 L 0 600 Z" fill="url(#grass)"/>
                <line x1="0" y1="400" x2="800" y2="400" stroke="#333" stroke-width="1"/>
                <polygon points="300,400 400,200 500,400" fill="#8B7355"/>
                <polyline points="0,380 200,360 400,350 600,340 800,350" fill="none" stroke="#333" stroke-width="2" marker-end="url(#arrowhead)"/>
                <ellipse cx="400" cy="420" rx="200" ry="20" fill="rgba(0,0,0,0.1)"/>
                <image href="cloud.png" x="100" y="50" width="120" height="60"/>
                <use href="#tree" x="150" y="300" width="40" height="80"/>
                <a href="https://xemantic.com" target="_blank">
                  <text x="400" y="560" text-anchor="middle" font-family="sans-serif" font-size="16">
                    <tspan font-weight="bold">Landscape </tspan><tspan fill="#666">Illustration</tspan>
                  </text>
                </a>
                <text class="path-label">
                  <textPath href="#curvePath">Text along a curved path</textPath>
                </text>
                <foreignObject x="620" y="460" width="160" height="80"/>
                <switch>
                  <g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Shape">
                    <rect x="10" y="10" width="100" height="40" fill="#eee"/>
                  </g>
                </switch>
              </g>
              <animateMotion dur="5s" repeatCount="indefinite" path="M 0 0 L 100 0"/>
              <animateTransform attributeName="transform" type="rotate" from="0 400 300" to="360 400 300" dur="60s" repeatCount="indefinite"/>
              <set attributeName="visibility" to="visible" begin="2s"/>
            </svg>
        """.trimIndent()
    }

}