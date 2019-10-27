package prockt.sketches.sk011

import prockt.KApplet

/*

    Rainbow lemniscate

 */
class Sketch11: KApplet() {

    companion object {
        const val ITERATIONS = 360
        const val OVERDRAW = 30
        const val SATURATION = 150f
    }

    var alphaDelta = 40

    override fun setup() {
        noStroke()
        colorMode(HSB)
        background(255)
    }

    override fun draw() {

        if (frameCount < ITERATIONS) {
            fill(map(frameCount, 0, ITERATIONS, 0, 255), SATURATION, 255f)
        } else {
            //Draw over the join
            alphaDelta += 10
            fill(map(frameCount - ITERATIONS, 0, ITERATIONS, 0, 255), SATURATION, 255f, 255f - alphaDelta)
        }

        translate((width / 2).toFloat(), (height / 2).toFloat())

        val x= sin(radians(frameCount.toFloat()))
        val y = sin(radians(frameCount.toFloat())) * cos(radians(frameCount.toFloat()))

        circle(x * 215, y * 205, 75f)

        if (frameCount == ITERATIONS + OVERDRAW) {
            noLoop()
        }
    }
}