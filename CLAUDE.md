# CLAUDE.md

This file captures only what cannot be inferred from the codebase itself.

## Rules for editing this file

Both developers and AI agents are expected to add entries as they encounter surprises.

- **Add an entry** when you encounter something unexpected: a build quirk, a non-obvious constraint, a dependency gotcha, or any behavior that would surprise the next agent or developer.
- **Add an entry** when a developer flags an anti-pattern produced by AI — describe the anti-pattern and the preferred alternative.
- **Do not** add codebase overviews, directory listings, or anything discoverable by reading the source.
- Keep entries concise: one line per lesson, grouped under a heading if a theme emerges.

## Known gotchas

### Testing

- `runTest { }` is required only when the test body actually calls a `suspend` function — otherwise use a plain `@Test fun`. In this project, `Flow<SemanticEvent>.render()` (from `com.xemantic.markanywhere.render`) and `should { }` from `com.xemantic.kotlin.test.coroutines` are suspend; `should { }` from `com.xemantic.kotlin.test` (non-coroutines) is not.

### DOM DSL semantics

- `invoke` REPLACES (clears the node, then builds); `plus` APPENDS (just builds). This applies symmetrically to both the `Node.invoke` / `Node.plus` extensions and to `NodeBuilder.invoke` / `NodeBuilder.plus`. Use `+ { }` to mutate properties (e.g. `element + { hidden = false }`) — calling `element { hidden = false }` would also clear all children.

## Anti-patterns to avoid

- Do not add content to this file that is already discoverable by reading the source or build scripts — that inflates context without adding signal, reducing AI agent task success rates (see [arxiv 2602.11988](https://arxiv.org/abs/2602.11988)).