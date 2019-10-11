package p5kt

import p5kt.gridutils.BasicCell

class Processing: KApplet() {

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