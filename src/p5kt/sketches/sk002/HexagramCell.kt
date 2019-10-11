package p5kt.sketches.sk002

import p5kt.KApplet
import p5kt.circle
import processing.core.PApplet.cos
import processing.core.PApplet.sin
import processing.core.PConstants.TWO_PI
import processing.core.PVector

class HexagramCell {

    companion object {
        const val RADS = TWO_PI/6
    }

    var hexPoints = arrayOfNulls<PVector>(6)

    fun draw(kappl: KApplet, origin: PVector, diam: Float, color: Int, opacity: Int){
        kappl.stroke(color, opacity.toFloat())

        for (i in 0 until 6) {
            val x = diam/4 * sin(RADS * i) + origin.x
            val y = diam/4 * cos(RADS * i) + origin.y
            kappl.circle(x, y, 2)
            hexPoints[i] = PVector(x, y)
        }

        val lines = kappl.random(30f).toInt() + 2

        for (i in 0 until lines) {
            val index1 = kappl.random(0f, 6f).toInt()
            val index2 = kappl.random(0f, 6f).toInt()
            kappl.line(hexPoints[index1]!!.x, hexPoints[index1]!!.y, hexPoints[index2]!!.x, hexPoints[index2]!!.y)
        }

        val rand = kappl.random(100f)

        if (rand < 55) {
            if (kappl.random(100f) < 50) {
                kappl.noFill()
            } else {
                kappl.fill(255)
            }
            kappl.circle(origin.x, origin.y, diam/4)
        } else if (rand < 10) {
            kappl.noFill()
            kappl.circle(origin.x, origin.y, diam/2)
        }
    }
}