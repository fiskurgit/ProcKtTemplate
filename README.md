# p5kt.P5Kt
![p5kt.P5Kt logo](p5kt.png)  

[Kotlin](https://kotlinlang.org/) wrapper for [p5kt.Processing](https://processing.org/). Clone, open in [Jetbrains IDEA](https://www.jetbrains.com/idea/), and start sketching.

```
class p5kt.Processing: p5kt.KApplet() {

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
p5kt.Processing requires JDK8, OpenJDK is fine: `brew cask install adoptopenjdk8`

## Licence

The p5kt.Processing core libraries are distributed under [GPL licence](LICENSE.md), this project is too.
