package prockt.sketches.archive

import processing.core.PShape
import prockt.KApplet

/*

    3D Galaxy

 */
class Sketch018: KApplet() {

    companion object {
        const val SHELLS = 16
        const val SHELL_STARS = 50000
    }

    private val shells = mutableListOf<PShape>()

    override fun settings() {
        mode(P3D)

    }

    override fun setup() {
        stroke(WHITE, 40)
        blendMode(ADD)
        var innerRad = 10
        var outerRad = 15

        repeat(SHELLS) { shellIndex ->
            val shell = createShape()
            shell.setStrokeWeight(1.75f)
            shell.beginShape(POINTS)

            if(shellIndex < SHELLS /2){
                val color = colorLerp("#9d2f4d", "#ffffff", shellIndex, SHELLS /2)
                shell.stroke(color, 50f)

            }else{
                val color = colorLerp("#ffffff", "#4973a1", shellIndex- SHELLS /2, SHELLS /2)
                shell.stroke(color, 50f)
            }


            repeat(SHELL_STARS) {
                val coord = randomSphereCoord(innerRad, outerRad)
                shell.vertex(coord.x, coord.y, coord.z)
            }

            shell.endShape()
            shells.add(shell)

            innerRad += 5
            outerRad += 15
        }
    }

    override fun draw() {
        background(BLACK)
        camera(-width/2, -height/2, 100)

//        fill(WHITE, 60)
//        noStroke()
//        sphere(40f)

        repeat(SHELLS){ index ->
            pushMatrix()
            rotateY((TWO_PI/(index*index)*frameCount)/100)
            rotateX(1.45f)
            rotateZ(-0.0f)
            shape(shells[index])
            popMatrix()
        }
    }
}