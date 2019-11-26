package prockt.sketches.sk032

import prockt.KApplet
import prockt.api.Coord

class Sketch032: KApplet() {

    lateinit var mirror: MirrorObject

    override fun setup() {
        mirror = MirrorObject(Coord(100, 100), 100f, radians(45f))
    }

    override fun draw() {
        background(BLACK)
        stroke(WHITE)

        circle(Coord(100, 100), 5)

        mirror.draw(this)
    }
}