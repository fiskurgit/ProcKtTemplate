package p5kt

import p5kt.gridutils.BasicCell

class Processing: KApplet() {

    override fun setup() {
        grid.prepopulate(BasicCell::class)
    }

    override fun draw() {
        background(BLACK)

        grid.occupants<BasicCell>().forEachIndexed { index, cell ->
            val color = colorLerp(color("#4973a1"), color("#9d2f4d"), index/grid.count().toFloat())
            cell.draw(this, grid.cellOrigin(index), grid.cellDiam(), color)
        }
    }

    override fun mouseMoved() {
        grid.occupantAt<BasicCell>(mouseX, mouseY).start()
    }
}