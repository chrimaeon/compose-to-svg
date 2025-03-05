# Compose SVG Renderer

This project is a Kotlin-based application that uses JetBrains Compose to render a UI and export it as an SVG file.

## Features

- **JetBrains Compose**: Utilizes JetBrains Compose for building the UI.
- **SVG Export**: Renders the UI to an SVG file.
## Usage

```kotlin
fun main(args: Array<String>) = svg(
    outputFilePath = "output.svg",
) {
    Text("Hello, World!")
}
```
