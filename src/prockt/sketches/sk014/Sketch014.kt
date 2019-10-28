package prockt.sketches.sk014

import prockt.KApplet

class Sketch014: KApplet() {

    companion object {
        const val MARGIN = 50f
    }

    override fun setup() {
        fill(EIGENGRAU)
    }

    override fun draw() {
        background(MOLNAR)

        circle(150, height - 400, 250)

        noStroke()
        circleLine(MARGIN, height/4, width - MARGIN, height/4, 20, 10)
        circleLine(MARGIN*2, height/1.2, 200, MARGIN, 12, 6)
        circleLine(width/2, MARGIN, width/1.2, height-250, 70, 60)
        circleLine(MARGIN, height- MARGIN, width/1.2, MARGIN * 2, 50, 15)
        circleLine(width/3, height - (MARGIN * 2), width - MARGIN, height - (MARGIN * 2), 12, 4)

        stroke(EIGENGRAU)
        dottedLine(width/3, height - (MARGIN * 4), width - MARGIN, height - (MARGIN * 4), 6)
    }
}