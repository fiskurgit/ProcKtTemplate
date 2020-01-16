package prockt.sketches.sk033

import processing.core.PApplet.cos
import processing.core.PApplet.sin
import prockt.KApplet
import prockt.api.Coord
import prockt.api.KVector
import prockt.api.Objekt
import prockt.KAppletApi.Companion.CYAN
import prockt.KAppletApi.Companion.WHITE
import prockt.api.ray.Ray

class MirrorObject(val coord: Coord, width: Float, rotationRad: Float): Objekt {

    private var start = KVector(coord.x - cos( rotationRad ) * width/2, coord.y - sin( rotationRad ) * width/2)
    private var end = KVector(coord.x + cos( rotationRad ) * width/2, coord.y + sin( rotationRad ) * width/2)

    override fun normal(coord: Coord?): KVector = KVector.normal(start, end)

    fun flip(){
        val temp = KVector(start.x, start.y)
        start = end.clone()
        end = temp.clone()
    }

    override fun collision(ray: Ray): Coord? {
        val uA = ((ray.end.x - ray.start.x) * (start.y - ray.start.y) - (ray.end.y - ray.start.y) * (start.x - ray.start.x)) / ((ray.end.y - ray.start.y) * (end.x - start.x) - (ray.end.x - ray.start.x) * (end.y - start.y))
        val uB = ((end.x - start.x) * (start.y - ray.start.y) - (end.y - start.y) * (start.x - ray.start.x)) / ((ray.end.y - ray.start.y) * (end.x - start.x) - (ray.end.x - ray.start.x) * (end.y - start.y))

        return when {
            uA in 0.0..1.0 && uB in 0.0..1.0 -> {
                val intersectionX = start.x + uA * (end.x - start.x)
                val intersectionY = start.y + uA * (end.y - start.y)

                Coord(intersectionX, intersectionY)
            }
            else -> null
        }
    }

    fun drawNormal(kapl: KApplet, length: Number) {
        val centroid = (start + end)/2
        val normal = normal()
        kapl.stroke(CYAN)
        kapl.line(centroid, KVector(centroid.x - normal.x * length.toFloat(), centroid.y - normal.y * length.toFloat()))
    }

    override fun draw(kapl: KApplet) {
        kapl.stroke(WHITE)
        kapl.line(start, end)
    }

    override fun reflection(ray: Ray): Ray? {
        val collision = collision(ray) ?: return null
        val normal = normal()
        val direction = ray.direction()

        val incidence = direction * -1f
        incidence.normalize()

        val dot = incidence.dot(normal)

        var reflection = KVector(-2f * normal.x * dot + incidence.x, -2f * normal.y * dot + incidence.y)
        reflection *= -1500f

        return Ray(collision, Coord(collision.x + reflection.x, collision.y + reflection.y))
    }
}