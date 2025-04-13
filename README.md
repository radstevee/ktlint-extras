# ktlint-extras

A small set of ktlint rules I made for myself that reflect
my coding style. Feel free to use them in your projects.
These are licensed under the MIT license.

## Rules

Currently there are three rules:

- Inline KDoc comments
- Forbid implicit 'it' usage
- Forbid interfaces prefixed with 'I'

## Usage

> [!WARNING]
> `ktlint-extras` does not support usage with spotless.

First up, add the repository:

```kts
repositories {
  maven("https://maven.radsteve.net/public")
}
```

### ktlint-gradle

For usage with ktlint-gradle, simply add the dependency
as a ktlint ruleset:

```kts
dependencies {
  ktlintRuleset("net.radsteve.ktlint.extras:rules:VERSION")
}
```

Replace `VERSION` with your desired version.
