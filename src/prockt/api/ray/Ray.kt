package prockt.api.ray

import processing.core.PApplet.cos
import processing.core.PApplet.sin
import prockt.KApplet
import prockt.KAppletApi.Companion.WHITE
import prockt.api.Coord
import prockt.api.KVector
import prockt.api.Objekt
import prockt.api.ray.renderer.RayRenderer

class Ray(var start: Coord, var end: Coord) {

    var originObject: Objekt? = null
    var alpha = 8f

    constructor(start: Coord, angle: Float, length: Float): this(start,
        Coord(start.x + (sin(angle) * length), start.y + (cos(angle) * length))
    )

    fun draw(kapl: KApplet, rayRenderer: RayRenderer){
        rayRenderer.draw(kapl, this)
        reduceAlpha()
    }

    fun draw(kapl: KApplet){
        kapl.stroke(WHITE, alpha)
        kapl.line(start, end)
        reduceAlpha()
    }

    fun draw(kapl: KApplet, color: Int){
        kapl.stroke(color)
        kapl.line(start, end)
        reduceAlpha()
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

    fun reduceAlpha(){
        alpha -= 0.3f
    }

    /*

        This is the object the beam originated from and is ignored when looking for collisions.
        It prevents a bug where a beam would get stuck in a mirror leading to a stack overflow.

     */
    fun setOrigin(objekt: Objekt?){
        this.originObject = objekt
    }

    fun clone(): Ray {
        val clone = Ray(start.clone(), end.clone())
        clone.alpha = alpha
        clone.setOrigin(originObject)
        return clone
    }

    override fun toString(): String {
        return "start: ${start.x}x${start.y} end: ${end.x}x${end.y}"
    }
}