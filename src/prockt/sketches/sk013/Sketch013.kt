package prockt.sketches.sk013

import processing.core.PConstants
import prockt.KApplet

/*

    Converting Vera Molnár's Un Deux Trois to ProcKt
    See: https://generativeartistry.com/tutorials/un-deux-trois/

 */
class Sketch013: KApplet() {

    companion object {
        const val STEP = 30
    }

    override fun setup() {
        strokeWeight(8f)
        strokeCap(PConstants.ROUND)
    }

    override fun draw() {
        background(MOLNAR)

        val aThird = height/3

        for(y in STEP until (height - STEP) step STEP) {
            for(x in STEP until (width - STEP) step STEP) {
                val molnarShape = createShape()
                molnarShape.beginShape(LINES)

                when {
                    y < aThird -> {
                        molnarShape.vertex(STEP * 0.5f, 0f)
                        molnarShape.vertex(STEP * 0.5f, STEP.toFloat())
                    }
                    y < (aThird * 2) -> {
                        molnarShape.vertex(STEP * 0.2f, 0f)
                        molnarShape.vertex(STEP * 0.2f, STEP.toFloat())

                        molnarShape.vertex(STEP * 0.8f, 0f)
                        molnarShape.vertex(STEP * 0.8f, STEP.toFloat())
                    }
                    else -> {
                        molnarShape.vertex(STEP * 0.1f, 0f)
                        molnarShape.vertex(STEP * 0.1f, STEP.toFloat())

                        molnarShape.vertex(STEP * 0.5f, 0f)
                        molnarShape.vertex(STEP * 0.5f, STEP.toFloat())

                        molnarShape.vertex(STEP * 0.9f, 0f)
                        molnarShape.vertex(STEP * 0.9f, STEP.toFloat())
                    }
                }

                molnarShape.endShape()

                pushMatrix()
                translate(x + STEP/2f, y + STEP/2f)//Move to cell center
                rotate(radians(random(360f)))//Random rotate around cell center
                translate(-STEP/2f, -STEP/2f)//Move back to cell origin
                shape(molnarShape)//Draw Shape
                popMatrix()
            }
        }

        noLoop()
    }

    override fun mouseClicked() {
        loop()
    }
}