package prockt.sketches.sk032

import processing.core.PApplet.cos
import processing.core.PApplet.sin
import prockt.KApplet
import prockt.api.Coord
import prockt.api.KVector
import prockt.api.Objekt
import prockt.KAppletApi.Companion.CYAN
import prockt.KAppletApi.Companion.WHITE
import prockt.api.Beam
import processing.core.PVector.dot
import processing.core.PVector
import kotlin.math.atan2


/*

    Reflection: https://processing.org/examples/reflection1.html

 */
class MirrorObject(val coord: Coord, width: Float, rotationRad: Float): Objekt {

    private var start = KVector(coord.x - cos( rotationRad ) * width/2, coord.y - sin( rotationRad ) * width/2)
    private var end = KVector(coord.x + cos( rotationRad ) * width/2, coord.y + sin( rotationRad ) * width/2)

    override fun normal(coord: Coord?): KVector = KVector.normal(start, end)

    fun flip(){
        val temp = KVector(start.x, start.y)
        start = end.clone()
        end = temp.clone()
    }

    fun collision(beam: Beam): Coord? {

        val x1 = start.x
        val y1 = start.y

        val x2 = end.x
        val y2 = end.y

        val x3 = beam.start.x
        val y3 = beam.start.y

        val x4 = beam.end.x
        val y4 = beam.end.y

        // calculate the distance to intersection point
        val uA = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1))
        val uB = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1))

        return when {
            uA in 0.0..1.0 && uB in 0.0..1.0 -> {
                val intersectionX = x1 + uA * (x2 - x1)
                val intersectionY = y1 + uA * (y2 - y1)

                Coord(intersectionX, intersectionY)
            }
            else -> null
        }
    }

    fun collision(x3: Float, y3: Float, x4: Float, y4: Float): Coord? {

        val x1 = start.x
        val y1 = start.y

        val x2 = end.x
        val y2 = end.y

        // calculate the distance to intersection point
        val uA = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1))
        val uB = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1))

        return when {
            uA in 0.0..1.0 && uB in 0.0..1.0 -> {
                val intersectionX = x1 + uA * (x2 - x1)
                val intersectionY = y1 + uA * (y2 - y1)

                Coord(intersectionX, intersectionY)
            }
            else -> null
        }
    }

    fun drawNormal(kapl: KApplet) {
        val centroid = (start + end)/2
        val normal = normal()
        kapl.stroke(CYAN)
        kapl.line(centroid, KVector(centroid.x - normal.x * 10, centroid.y - normal.y * 10))
    }

    override fun draw(kapl: KApplet) {
        kapl.stroke(WHITE)
        kapl.line(start, end)
    }

    fun reflection(beam: Beam): Beam? {
        val collision = collision(beam) ?: return null
        val normal = normal()
        val direction = beam.direction()
        val incidence = PVector.mult(direction, -1f)
        incidence.normalize()
        val dot = incidence.dot(normal.toPVector())

        var vc = PVector()
        vc.set(-2f * normal.x * dot + incidence.x, -2f * normal.y * dot + incidence.y)
        vc.mult(-1500f)
        val b = Beam(collision, Coord(collision.x + vc.x, collision.y + vc.y))

        return b
    }
}