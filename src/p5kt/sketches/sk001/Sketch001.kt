package p5kt.sketches.sk001

import p5kt.KApplet
import p5kt.colorLerp
import p5kt.gridutils.BasicCell

class Sketch001: KApplet() {

    override fun settings() {
        size(600, 600)
        super.settings()
    }

    override fun setup() {
        grid.prepopulate(BasicCell::class)
    }

    override fun draw() {
        background(BLACK)

        grid.occupants<BasicCell>().forEachIndexed { index, cell ->
            val color = colorLerp("#4973a1", "#9d2f4d", index, grid.count())
            cell.draw(this, grid.cellOrigin(index), grid.cellDiam(), color)
        }
    }

    override fun mouseMoved() {
        grid.occupantAt<BasicCell>(mouseX, mouseY).start()
    }
}