package prockt.sketches.sk005

import processing.core.PApplet.cos
import processing.core.PApplet.sin
import processing.core.PConstants
import processing.core.PConstants.CLOSE
import processing.core.PVector
import prockt.KApplet
import prockt.KAppletApi.Companion.WHITE

class DotFillCell {

    fun draw(kappl: KApplet, origin: PVector, diam: Float){

        val shapeImage = kappl.createGraphics((diam*2).toInt(), (diam*2).toInt())
        val maskImage = kappl.createGraphics((diam*2).toInt(), (diam*2).toInt())

        //1. Create mask
        maskImage.beginDraw()
        maskImage.noStroke()
        maskImage.fill(WHITE)

        val dotSpacing = kappl.random(6f, 20f).toInt()

        for(x in 0..(diam*2).toInt() step dotSpacing){
            for(y in 0..(diam*2).toInt() step dotSpacing){
                if(y % 2 == 0){
                    maskImage.ellipse(x.toFloat(), y.toFloat(), 2f, 2f)
                }else{
                    maskImage.ellipse(x.toFloat() - dotSpacing/2, y.toFloat(), 2f, 2f)
                }

            }
        }

        maskImage.endDraw()

        //2. Create shape
        shapeImage.beginDraw()

        val sides = kappl.random(3f, 7f).toInt()
        val rads = PConstants.TWO_PI /sides

        val dotShape = kappl.createShape()
        dotShape.beginShape()
        dotShape.noStroke()
        dotShape.fill(WHITE)

        for(i in 0..sides){
            val x = diam * sin(rads * i)
            val y = diam * cos(rads * i)
            dotShape.vertex(x, y)
        }

        dotShape.endShape(CLOSE)
        dotShape.rotate(kappl.random(0f, PConstants.TWO_PI))
        shapeImage.shape(dotShape, diam, diam)
        shapeImage.endDraw()

        //3. Mask and Draw
        shapeImage.mask(maskImage)
        kappl.image(shapeImage, origin.x-diam, origin.y-diam)
    }
}