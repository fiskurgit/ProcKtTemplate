package prockt.sketches.sk002

import prockt.KApplet

/*

    Hexagrams

 */
class Sketch002: KApplet() {

    override fun setup() {
        grid.prepopulate(HexagramCell::class)
    }

    override fun draw() {
        background(WHITE)

        grid.occupants<HexagramCell>().forEachIndexed { index, cell ->
            cell.draw(this, grid.cellOrigin(index), grid.cellDiam(), BLACK, 65)
        }

        noLoop()
    }

    override fun mousePressed() {
        loop()
    }
}