# P5Kt
![P5Kt logo](p5kt.png)  

Kotlin wrapper for Processing. Clone, open in Jetbrains IDEA, and start sketching.

```
class Processing: KApplet() {

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
Processing requires JDK8, OpenJDK is fine: `brew cask install adoptopenjdk8`

## Licence

The Processing core libraries are distributed under [GPL licence](LICENSE.md), this project is too.
