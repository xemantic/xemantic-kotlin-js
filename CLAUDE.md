# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
./gradlew build                    # Build and test all targets
./gradlew jsTest                   # Run JS tests only
./gradlew wasmJsTest               # Run WASM tests only
./gradlew jsBrowserTest            # Run JS browser tests
./gradlew jsNodeTest               # Run JS Node.js tests
./gradlew dependencyUpdates        # Check for dependency updates (use --no-parallel)
```

## Project Overview

This is a Kotlin multiplatform library (`com.xemantic.kotlin:xemantic-kotlin-js`) providing JavaScript utilities, targeting:
- **js** (browser + Node.js)
- **wasmJs** (browser + Node.js + d8)

The library requires browser DOM support, so platforms without DOM access (like wasmWasi, native) are not supported.

## Architecture

### DOM DSL (`src/jsMain/kotlin/`)

The core feature is a type-safe DSL for building DOM trees:

- **DomDsl.kt**: Core DSL infrastructure
  - `@DomDsl` marker annotation for scope control
  - `Node.invoke()` extension to start building children on any existing node
  - `NodeBuilder` class with:
    - `String.invoke()` for arbitrary element names (e.g., `"my:component" { }`)
    - `element()` function for creating typed elements
    - `String.unaryPlus()` for text nodes (`+"Hello"`)

- **HtmlElements.kt**: Type-safe HTML element functions (`div`, `button`, `span`, `a`) that provide properly typed element receivers (e.g., `HTMLDivElement`, `HTMLButtonElement`)

### Usage Pattern

```kotlin
node { div("className") {
    button("btn") {
        it.onclick = { }
        +"Click me"
    }
}}
```

## Testing

Uses `xemantic-kotlin-test` with power-assert. Test assertions use `assert` and `have` functions from `com.xemantic.kotlin.test`.

## Code Style

- Explicit API mode enabled (`explicitApi()`)
- Kotlin context parameters and context-sensitive resolution enabled
- All public API requires explicit visibility modifiers