package processingkt.sketches.sk003

import processingkt.KApplet
import processingkt.stroke

class Sketch003: KApplet() {

    override fun setup() {
        grid.prepopulate(20, 20, RandomisedCell::class)
        stroke(BLACK, 150)
    }

    override fun draw() {
        background(WHITE)

        grid.occupants<RandomisedCell>().forEachIndexed { index, cell ->
            cell.draw(this, grid.cellOrigin(index), grid.cellDiam())
        }

        noLoop()
    }

    override fun mousePressed() {
        loop()
    }
}