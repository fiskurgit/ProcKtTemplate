# ProcessingKt
![ProcessingKt logo](processingkt.png)  

[Kotlin](https://kotlinlang.org/) wrapper for [Processing](https://processing.org/). Clone, open in [Jetbrains IDEA](https://www.jetbrains.com/idea/), and start sketching.

```
class Sketch: KApplet() {

    override fun settings() {
        size(600, 600)
        super.settings()
    }

    override fun draw() {
        background(BLACK)
        stroke(WHITE)
        line(0, 0, width, height)
    }
}

```

## Note.
Processing requires JDK8, OpenJDK is fine: `brew cask install adoptopenjdk8`

## Licence

The Processing core libraries are distributed under [GPL licence](LICENSE.md), this project is too.
