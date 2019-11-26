package prockt.sketches.sk032

import processing.core.PApplet.cos
import processing.core.PApplet.sin
import prockt.KApplet
import prockt.api.Coord
import prockt.api.KVector
import prockt.api.Objekt

class MirrorObject(var coord: Coord?, var width: Float, var rotationRad: Float): Objekt {

    private var startCoord: Coord = Coord(coord!!.x - cos( rotationRad ) * width/2, coord!!.y - sin( rotationRad ) * width/2)
    private var endCoord: Coord = Coord(coord!!.x + cos( rotationRad ) * width/2, coord!!.y + sin( rotationRad ) * width/2)


    override fun normal(coord: Coord?): KVector? {
        return null
    }

    override fun draw(kapl: KApplet) {
        if(coord == null) return
        kapl.line(startCoord, endCoord)
    }
}