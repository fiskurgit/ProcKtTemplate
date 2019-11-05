package prockt.sketches

import processing.core.PApplet
import processing.core.PShape
import prockt.KApplet

class Sketch019: KApplet() {
    companion object {
        const val SHELLS = 4
        const val SHELL_STARS = 10000
        const val RINGS = 50
        const val RING_STARS = 3000
    }
    
    private val shells = mutableListOf<PShape>()
    private val rings = mutableListOf<PShape>()

    override fun settings() {
        mode(P3D)
    }
    override fun setup() {

        blendMode(ADD)

        var innerRad = 1f
        var outerRad = 3f

        repeat(SHELLS) { shellIndex ->
            val shell = createShape()
            shell.setStrokeWeight(1f)
            shell.beginShape(POINTS)

            val color = colorLerp("#4973a1", "#ffffff", shellIndex, SHELLS)
            shell.stroke(color, 80f)

            repeat(SHELL_STARS) {
                val coord = randomSphereCoord(innerRad, outerRad)
                shell.vertex(coord.x, coord.y, coord.z)
            }

            shell.endShape()
            shells.add(shell)

            innerRad += 5
            outerRad += 16
        }

        innerRad = 1f
        outerRad = 9f
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

        camera(-width/2, -height/2, 100)

        fill(WHITE)
        sphere(4f)

        repeat(SHELLS){ index ->
            pushMatrix()
            rotateY((TAU /(index*index)*frameCount)/100)
            rotateX(1.45f)
            rotateZ((TAU /(index*index)*frameCount)/100)
            shape(shells[index])
            popMatrix()
        }

        repeat(RINGS){ index ->
            pushMatrix()
            rotateY((TWO_PI/(index*index)*frameCount)/10)
            rotateX(1.45f)
            rotateZ(-2.7f)
            shape(rings[index])
            popMatrix()
        }
    }
}