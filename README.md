# p5kt.P5Kt
![p5kt.P5Kt logo](p5kt.png)  

[Kotlin](https://kotlinlang.org/) wrapper for [p5kt.sketches.sk001.Sketch001](https://processing.org/). Clone, open in [Jetbrains IDEA](https://www.jetbrains.com/idea/), and start sketching.

```
class p5kt.sketches.sk001.Sketch001: p5kt.KApplet() {

    override fun setup() {
        stroke(WHITE)
    }

    override fun draw() {
        background(BLACK)
        line(0, 0, width, height)
    }
}

```

## Note.
p5kt.sketches.sk001.Sketch001 requires JDK8, OpenJDK is fine: `brew cask install adoptopenjdk8`

## Licence

The p5kt.sketches.sk001.Sketch001 core libraries are distributed under [GPL licence](LICENSE.md), this project is too.
