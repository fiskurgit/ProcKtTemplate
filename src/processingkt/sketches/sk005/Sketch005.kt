package processingkt.sketches.sk005

import processingkt.KApplet
import processingkt.stroke

class Sketch005: KApplet() {

    override fun setup() {
        grid.prepopulate(4, 4, DotFillCell::class)
        stroke(WHITE, 150)
    }


    override fun draw() {
        background(BLACK)

        grid.occupants<DotFillCell>().forEachIndexed { index, cell ->
            cell.draw(this, grid.cellOrigin(index), grid.cellDiam()/3)
        }

        noLoop()
    }

    override fun mousePressed() {
        loop()
    }
}