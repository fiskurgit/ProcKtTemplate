package prockt.sketches.archive

import prockt.KApplet
import processing.core.PShape

/*
     Asteroid belt using 'randomCircleCoord(innerRadius, outerRadius)'
 */
class Sketch016 : KApplet() {

    private var ring: PShape? = null

    override fun settings() {
        mode(P3D)
    }

    override fun setup() {
        stroke(WHITE, 100)

        ring = createShape()
        ring?.setStrokeWeight(1.5f)
        ring?.beginShape(POINTS)

        repeat(50000) {
            val coord = randomCircleCoord(200, 280)
            val z = random(-10f, 10f)
            ring?.vertex(coord.x, coord.y, z)
        }

        ring?.endShape()
    }

    override fun draw() {
        background(BLACK)
        camera(-width/2, -height/2, 150)

        rotateY(PI/60f*frameCount)
        rotateX(1.45f)
        rotateZ(-0.23f)

        shape(ring!!)
    }
}