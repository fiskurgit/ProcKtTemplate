package prockt.sketches.sk009

import prockt.KApplet

class Sketch009: KApplet() {

    override fun setup() {
        stroke(WHITE, 150)
    }

    override fun draw() {
        background(BLACK)
        repeat(50000){
            pushMatrix()
            translate(width/4, height/2)
            point(randomCircleCoordB(width/4))
            popMatrix()

            pushMatrix()
            translate((width/4) * 3, height/2)
            point(randomCircleCoord(width/4))
            popMatrix()
        }

        noLoop()
    }

    override fun mouseClicked() {
        loop()
    }

}