package prockt.api.ray.renderer

import processing.core.PApplet.*
import prockt.KApplet
import prockt.KAppletApi.Companion.WHITE
import prockt.api.Coord
import prockt.api.ray.Ray

class PointRenderer: RayRenderer {

    override fun draw(kapl: KApplet, ray: Ray) {
        val start = ray.start.toVector()
        val end = ray.end.toVector()

        val angle = atan2(start.y - end.y, start.x - end.x)

        val stepSize = 8f
        var steps = (ray.length()/stepSize).toInt()

        val maxSteps = (kapl.getMaximumLineLength()/stepSize).toInt()
        if(steps > maxSteps){
            steps = maxSteps
        }

        repeat(steps){
            val pointX = ray.start.x + (sin(angle) * (it * stepSize))
            val pointY = ray.start.y + (cos(angle) * (it * stepSize))

            val pointCoord = Coord(pointX, pointY)

            kapl.stroke(WHITE, ray.alpha)
            kapl.strokeWeight(2f)
            kapl.point(pointCoord)
        }

        kapl.stroke(WHITE, 30)
        kapl.point(end.coord())

    }
}