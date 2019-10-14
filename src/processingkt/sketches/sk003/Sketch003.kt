package processingkt.sketches.sk003

import processingkt.KApplet
import processingkt.stroke

class Sketch003: KApplet() {

    override fun settings() {
        size(600, 600, P3D)
        grid.columns = 20
        grid.rows = 20
        super.settings()
    }

    override fun setup() {
        grid.prepopulate(RandomisedCell::class)
        stroke(BLACK, 150)
    }

    override fun draw() {
        background(WHITE)

        grid.occupants<RandomisedCell>().forEachIndexed { index, cell ->
            cell.draw(this, grid.cellOrigin(index), grid.cellDiam())
        }

        println("ones: ${RandomisedCell.ones}")
        println("twos: ${RandomisedCell.twos}")
        println("threes: ${RandomisedCell.threes}")
        println("fours: ${RandomisedCell.fours}")
        println("fives: ${RandomisedCell.fives}")

        noLoop()
    }

    override fun mousePressed() {
        loop()
    }
}