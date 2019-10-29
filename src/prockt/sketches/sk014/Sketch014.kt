package prockt.sketches.sk014

import prockt.KApplet

/*

    Autechre artwork

 */
class Sketch014: KApplet() {

    companion object {
        const val DIAM = 100
    }

    override fun setup() {
        fill(EIGENGRAU)
        stroke(EIGENGRAU)
        frameRate(0.5f)
    }

    override fun draw() {
        background(WHITE)

        val aCount = random(1, 25)
        for (i in 0 until aCount) {
            drawTriangle()
        }

        val bCount = random(1, 25)
        for (i in 0 until bCount) {
            drawQuad()
        }

        drawBorder()
    }

    private fun drawTriangle() {
        val f = random(-3, 3)
        var lw = DIAM
        if (f < 0) {
            lw = DIAM / f
        }
        if (f > 0) {
            lw = f * DIAM
        }
        val x = random(0, width / DIAM) * DIAM
        val y = random(0, height / DIAM) * DIAM
        triangle(x.toFloat(), y.toFloat(), (x + lw).toFloat(), y.toFloat(), (x + lw).toFloat(), (y + lw).toFloat())
    }

    private fun drawQuad() {
        val ww = random(-2, 3) * DIAM
        val i = random(0, width / DIAM)
        val sx = i * ww
        val ex = sx + i * ww
        val sy = i * ww
        val ey = sy + i * ww
        val xo = random(-width / DIAM, width / DIAM) * DIAM

        beginShape()
        vertex((sx + xo).toFloat(), sy.toFloat())
        vertex((sx + ww + xo).toFloat(), sy.toFloat())
        vertex((ex + ww + xo).toFloat(), ey.toFloat())
        vertex((ex + ww + xo).toFloat(), (ey + ww).toFloat())
        vertex((ex + xo).toFloat(), ey.toFloat())
        vertex((sx + xo).toFloat(), sy.toFloat())
        endShape()
    }

    private fun drawBorder() {
        stroke(WHITE)
        noFill()
        rect(0, 0, width, height)
        fill(EIGENGRAU)
        stroke(EIGENGRAU)
    }
}