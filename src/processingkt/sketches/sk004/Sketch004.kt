package processingkt.sketches.sk004

import processingkt.KApplet
import processingkt.stroke

class Sketch004: KApplet() {

    override fun setup() {
        grid.prepopulate(4, 4, SnowflakeCell::class)
        stroke(WHITE, 150)
    }


    override fun draw() {
        background(BLACK)

        startPdf()

        grid.occupants<SnowflakeCell>().forEachIndexed { index, cell ->
            cell.draw(this, grid.cellOrigin(index), grid.cellDiam()/3)
        }

        noLoop()

        endPdf()
    }

    override fun mousePressed() {
        loop()
    }
}