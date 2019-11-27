package prockt.api

import processing.core.PApplet.cos
import processing.core.PApplet.sin
import prockt.KApplet

class Beam(var start: Coord, var end: Coord) {

    var originObject: Objekt? = null

    constructor(start: Coord, angle: Float, length: Float): this(start, Coord(start.x + (sin(angle) * length), start.y + (cos(angle) * length)))

    fun draw(kapl: KApplet, color: Int){
        kapl.stroke(color)
        kapl.line(start, end)
    }

    fun direction(): KVector {
        return KVector(end.x - start.x, end.y - start.y)
    }

    fun length(): Float{
        return start.dist(end)
    }

    fun flip(){
        val temp = start.clone()
        start = end
        end = temp
    }

    /*

        This is the object the beam originated from and is ignored when looking for collisions.
        It prevents a bug where a beam would get stuck in a mirror leading to a stack overflow.

     */
    fun setOrigin(objekt: Objekt?){
        this.originObject = objekt
    }

    fun clone(): Beam {
        val clone = Beam(start.clone(), end.clone())
        clone.setOrigin(originObject)
        return clone
    }

    override fun toString(): String {
        return "start: ${start.x}x${start.y} end: ${end.x}x${end.y}"
    }
}