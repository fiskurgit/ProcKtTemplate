package prockt.sketches.sk010

import prockt.KApplet

/*

    Weighted random circle grid

 */
class Sketch010: KApplet() {

    override fun setup() {
        grid.columns = 6
        grid.rows = 6
        grid.prepopulate(RndCircleCell::class)
        stroke(WHITE, 180)
    }

    override fun draw() {
        background(BLACK)

        grid.occupants<RndCircleCell>().forEachIndexed { index, cell ->
            cell.draw(this, index, grid.cellDiam(), grid.cellOrigin(index))
        }

        noLoop()
    }

    override fun mouseClicked() {
        loop()
    }
}