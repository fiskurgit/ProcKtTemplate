package prockt.sketches.sk015

import prockt.KApplet

/*

   Evenly distributed random coordinates within an annulus

 */
class Sketch015: KApplet() {

    override fun setup() {
        stroke(EIGENGRAU, 100)
    }

    override fun draw() {
        background(MOLNAR)

        translate(width/2, height/2)

        repeat(25000){
            point(randomCircleCoord(240, 280))
            point(randomCircleCoord(160, 200))
            point(randomCircleCoord(80, 120))
            point(randomCircleCoord(0, 40))
        }

        noLoop()
    }

    override fun mouseClicked() {
        loop()
    }
}