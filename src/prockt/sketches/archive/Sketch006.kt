package prockt.sketches.archive

import processing.core.PGraphics
import prockt.KApplet

/*

    Shape mask (large)

 */
class Sketch006: KApplet() {

    lateinit var shapeImage: PGraphics
    lateinit var maskImage: PGraphics

    override fun setup() {
        maskImage = createGraphics(width, height)
        shapeImage = createGraphics(width, height)
    }

    override fun draw() {
        background(BLACK)

        generateMask()
        generateShape()

        shapeImage.mask(maskImage)
        image(shapeImage)

        noLoop()
    }

    override fun mousePressed() {
        loop()
    }

    private fun generateShape() {
        val circle = createShape(ELLIPSE, 0f, 0f, width.toFloat()/1.2f, height.toFloat()/1.2f)

        shapeImage.beginDraw()
        shapeImage.clear()
        shapeImage.shape(circle, width/2.toFloat(), height/2.toFloat())
        shapeImage.endDraw()
    }

    private fun generateMask() {
        maskImage.beginDraw()
        maskImage.clear()
        maskImage.noStroke()
        maskImage.fill(WHITE)

        val dotSpacing = random(10f, 20f).toInt()

        for(x in 0..(width) step dotSpacing){
            for(y in 0..(height) step dotSpacing){
                if(random(100f) > 1) {
                    if (y % 2 == 0) {
                        maskImage.ellipse(x.toFloat(), y.toFloat(), 2f, 2f)
                    } else {
                        maskImage.ellipse(x.toFloat() - dotSpacing / 2, y.toFloat(), 2f, 2f)
                    }
                }
            }
        }

        maskImage.endDraw()
    }
}