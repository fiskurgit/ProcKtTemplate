package processingkt.sketches.sk006

import processing.core.PShape
import processingkt.KApplet

class Sketch006: KApplet() {

    override fun settings() {
        size(600, 600)
        super.settings()
    }


    override fun draw() {
        background(BLACK)

        val shapeImage = createGraphics(width, height)
        val maskImage = createGraphics(width, height)

        val shape = generateShape()


        noLoop()
    }

    override fun mousePressed() {
        loop()
    }

    private fun generateShape(): PShape {

        return PShape()
    }
}