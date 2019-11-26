package prockt.api

import processing.core.PApplet.cos
import processing.core.PApplet.sin
import processing.core.PVector
import prockt.KApplet
import prockt.sketches.sk032.MirrorObject

class Beam(val start: Coord, val end: Coord) {

    constructor(start: Coord, angle: Float, length: Float): this(start, Coord(start.x + (sin(angle) * length), start.y + (cos(angle) * length)))

    fun draw(kapl: KApplet, color: Int){
        kapl.stroke(color)
        kapl.line(start, end)
    }

    fun direction(): PVector {
        return PVector(end.x - start.x, end.y - start.y)
    }

    fun length(): Float{
        return start.toVector().toPVector().dist(end.toVector().toPVector())
    }

    var originMirror: MirrorObject? = null

    fun setOrigin(originMirror: MirrorObject?){
        this.originMirror = originMirror
    }
}