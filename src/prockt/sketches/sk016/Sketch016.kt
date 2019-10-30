package prockt.sketches.sk016

import prockt.KApplet
import processing.core.PShape

/*
     Asteroid belt using 'randomCircleCoord(innerRadius, outerRadius)'
 */
class Sketch016 : KApplet() {

    private var xRot: Float = 0f
    private var zRot: Float = 0f
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

        rotateY(xRot)
        rotateZ(zRot)

        shape(ring!!)
    }

    override fun mouseDragged() {
        xRot += (pmouseY - mouseY) * 0.01f
        zRot += (mouseX - pmouseX) * 0.01f
    }
}