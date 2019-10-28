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
            cell.draw(index, grid.cellDiam(), grid.cellOrigin(index))
        }

        noLoop()
    }

    override fun mouseClicked() {
        loop()
    }

    inner class RndCircleCell{
        fun draw(index: Int, diam: Float, origin: PVector){
            repeat((index+1) * 30){
                val coord = randomCircleCoordWeighted(diam/3)
                coord.x += origin.x
                coord.y += origin.y
                point(coord)
            }
        }
    }
}