package prockt.sketches.sk010

import processing.core.PVector
import prockt.KApplet

class RndCircleCell{
    fun draw(kapl: KApplet, index: Int, diam: Float, origin: PVector){
        repeat((index+1) * 30){
            val coord = kapl.randomCircleCoordWeighted(diam/3)
            coord.x += origin.x
            coord.y += origin.y
            kapl.point(coord)
        }
    }
}