package prockt.sketches.archive.sk004

import prockt.KApplet

/*

    Snowflakes

 */
class Sketch004: KApplet() {
    
    override fun setup() {
        grid.prepopulate(3, 3, SnowflakeCell::class)
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