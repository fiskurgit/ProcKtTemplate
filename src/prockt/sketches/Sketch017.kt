package prockt.sketches

import processing.core.PShape
import prockt.KApplet

/*

    Ring galaxy (2.5D)

 */
class Sketch017: KApplet() {

    companion object {
        const val RINGS = 50
        const val RING_STARS = 3000
    }

    private val rings = mutableListOf<PShape>()

    override fun settings() {
        mode(P3D)

    }

    override fun setup() {
        stroke(WHITE, 40)
        blendMode(ADD)
        var innerRad = 1f
        var outerRad = 9f
        var increment = 1f

        repeat(RINGS) { ringIndex ->
            val ring = createShape()
            ring.setStrokeWeight(1.5f)
            ring.beginShape(POINTS)

            val color = colorLerp("#4973a1", "#9d2f4d", ringIndex, RINGS)
            ring.stroke(color, 50f)

            repeat(RING_STARS * (ringIndex/2)) {
                val coord = randomCircleCoord(innerRad, outerRad)
                val z = random(-increment, increment)
                ring.vertex(coord.x, coord.y, z)
            }

            ring.endShape()
            rings.add(ring)

            innerRad += increment
            outerRad += increment*1.5f
            increment += 1
        }
    }

    override fun draw() {
        background(BLACK)
        camera(-width/2, -height/2, 150)

        repeat(RINGS){ index ->
            pushMatrix()
            rotateY((TWO_PI/(index*index)*frameCount)/10)
            rotateX(1.45f)
            rotateZ(-0.0f)
            shape(rings[index])
            popMatrix()
        }
    }
}