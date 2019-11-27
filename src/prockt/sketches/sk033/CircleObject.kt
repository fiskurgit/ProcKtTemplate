package prockt.sketches.sk033

import processing.core.PApplet.sqrt
import prockt.KApplet
import prockt.KAppletApi.Companion.RED
import prockt.KAppletApi.Companion.WHITE
import prockt.api.Beam
import prockt.api.Coord
import prockt.api.KVector
import prockt.api.Objekt

class CircleObject(val coord: Coord, val diameter: Float): Objekt {

    //http://studio.processingtogether.com/sp/pad/export/ro.9gGiJpQZIcTte/latest
    override fun collision(_beam: Beam): Coord? {
        val beam = _beam.clone()
        beam.flip()
        val dx = beam.end.x - beam.start.x
        val dy = beam.end.y - beam.start.y

        val a = dx * dx + dy * dy
        val b = (dx * (beam.start.x - coord.x) + (beam.start.y - coord.y) * dy) * 2
        var c = coord.x * coord.x + coord.y * coord.y

        c += beam.start.x * beam.start.x + beam.start.y * beam.start.y
        c -= (coord.x * beam.start.x + coord.y * beam.start.y) * 2
        c -= (diameter/2f) * (diameter/2f)

        var delta = b * b - 4 * a * c

        if (delta < 0) return null

        delta = sqrt(delta)

        val mu = (-b + delta) / (2 * a)
        val ix1 = beam.start.x + mu * dx
        val iy1 = beam.start.y + mu * dy

        val collisionOnPlane = Coord(ix1, iy1)

        val startDist = collisionOnPlane.dist(_beam.start)
        val endDist = collisionOnPlane.dist(_beam.end)

        return when {
            startDist < endDist -> null
            else -> collisionOnPlane
        }
    }

    override fun reflection(_beam: Beam): Beam? {
        var beam = _beam.clone()

        val collision = collision(beam) ?: return null
        val normal = normal(collision)

        beam.flip()
        val direction = beam.direction()

        val incidence = direction * -1f
        incidence.normalize()

        val dot = incidence.dot(normal)

        val reflection = KVector(-2f * normal.x * dot + incidence.x, -2f * normal.y * dot + incidence.y)
        reflection.normalise()

        return Beam(collision, Coord(collision.x + reflection.x, collision.y + reflection.y))
    }

    fun drawNormal(kapl: KApplet, pCoord: Coord?){
        if(pCoord == null) return

        var n = normal(pCoord)
        n *= 30
        kapl.stroke(RED)
        kapl.line(pCoord.x, pCoord.y, pCoord.x + n.x, pCoord.y + n.y)
    }

    override fun normal(pCoord: Coord?): KVector {
        if(pCoord == null) return KVector.empty()

        val center = KVector(coord)
        val collision = KVector(pCoord)
        val direction = center.direction(collision)
        direction.normalise()
        return direction
    }

    override fun draw(kapl: KApplet) {
        kapl.fill(WHITE)
        kapl.circle(coord, diameter)
    }



}