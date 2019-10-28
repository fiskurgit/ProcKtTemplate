package prockt.sketches.sk013

import processing.core.PConstants
import prockt.KApplet

class Sketch013: KApplet() {

    companion object {
        const val STEP = 20
    }

    override fun settings() {
        size(320)
    }

    override fun setup() {
        background(MOLNAR)
        stroke(BLACK)
        noFill()
        strokeWeight(5f)
        strokeCap(PConstants.ROUND)
    }

    override fun draw() {

        val aThird = height/3

        for(y in STEP until (height - STEP) step STEP) {
            for(x in STEP until (width - STEP) step STEP) {
                strokeWeight(0.5f)
                square(x, y, STEP)
                strokeWeight(3f)

                val molnarShape = createShape()
                molnarShape.beginShape()

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
                molnarShape.rotate(random(TWO_PI))
                pushMatrix()

                translate(x, y)
                shape(molnarShape)

                popMatrix()
            }
        }

        noLoop()
    }

    override fun mouseClicked() {
        loop()
    }
}