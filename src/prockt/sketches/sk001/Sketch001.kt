package prockt.sketches.sk001

import prockt.KApplet
import prockt.colorLerp
import prockt.dashedLine
import prockt.dottedLine


class Sketch001: KApplet() {

    override fun setup() {
        grid.prepopulate(BasicCell::class)
    }

    override fun draw() {
        background(BLACK)

        grid.draw(this, WHITE, 55f)

        grid.occupants<BasicCell>().forEachIndexed { index, cell ->
            val color = colorLerp("#4973a1", "#9d2f4d", index, grid.count())
            cell.draw(this, grid.cellOrigin(index), grid.cellDiam(), color)
        }

        stroke(255)
        dashedLine(0, 0, width, height, 5)
        dottedLine(width, 0, 0, height, 10)
    }

    override fun mouseMoved() {
        grid.occupantAt<BasicCell>(mouseX, mouseY).start()
    }
}