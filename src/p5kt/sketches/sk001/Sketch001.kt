package p5kt.sketches.sk001

import p5kt.KApplet
import p5kt.colorLerp

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

        grid.draw(this, WHITE, 20f)

        grid.occupants<BasicCell>().forEachIndexed { index, cell ->
            val color = colorLerp("#4973a1", "#9d2f4d", index, grid.count())
            cell.draw(this, grid.cellOrigin(index), grid.cellDiam(), color)
        }
    }

    override fun mouseMoved() {
        grid.occupantAt<BasicCell>(mouseX, mouseY).start()
    }
}