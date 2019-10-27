package prockt.sketches.sk010

import processing.core.PVector
import prockt.KApplet

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

    class RndCircleCell{
        fun draw(kapl: KApplet, index: Int, diam: Float, origin: PVector){
            repeat((index+1) * 30){
                val coord = kapl.randomCircleCoordB(diam/3)
                coord.x += origin.x
                coord.y += origin.y
                kapl.point(coord)
            }
        }
    }
}