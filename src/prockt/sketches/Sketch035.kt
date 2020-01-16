package prockt.sketches

import prockt.KApplet

/*

    öppenteknikstudio logo playground

 */
class Sketch035: KApplet() {

    private val diam = 320
    var res = 50
    var lastX = 0f
    var lastY = 0f

    override fun settings() {
        size(diam, diam, P3D)
        smooth(8)
        super.setup()
    }

    override fun setup() {
        stroke(BLACK)
        strokeWeight(2.0f)
        noLoop()

    }

    override fun draw() {
        background(WHITE)
        translate((width / 2).toFloat(), (height / 2).toFloat())

        for (i in 0..res) {
            val th = i * (PI / res)
            val r = sin(9 * th)

            val x = r * cos(th) * 75f
            val y = r * sin(th) * 75f

            line(lastX, lastY, x, y)

            lastX = x
            lastY = y
        }
    }
}