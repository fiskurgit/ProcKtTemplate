package prockt.sketches

import processing.core.PGraphics
import processing.core.PImage
import prockt.KApplet
import prockt.OneBitFilter
import prockt.api.Coord

/*

    Bouncing dithered spheres

 */
class Sketch008 : KApplet() {

    companion object {
        const val DIAM = 600
        const val SPHERE_RAD = 40f
        const val SPHERE_COUNT = 160
        const val BOUNDARY = SPHERE_RAD * 2
        val FILTER = OneBitFilter.get("Stucki").threshold(255)
    }

    data class CoordVector(var coord: Coord, var xDirection: Int, var yDirection: Int, var speed: Float)

    private var sourceImage: PGraphics? = null
    private var filteredImage: PImage? = null
    private var coords = mutableListOf<CoordVector>()

    override fun settings() {
        size(DIAM, DIAM, P2D)
    }

    override fun setup() {
        sourceImage = createGraphics(
            DIAM,
            DIAM, P3D)
        filteredImage = PImage(
            DIAM,
            DIAM
        )

        repeat(SPHERE_COUNT) {
            val coord = Coord(random(BOUNDARY, DIAM - BOUNDARY),random(
                BOUNDARY, DIAM - BOUNDARY
            ))
            val coordVector =
                CoordVector(
                    coord,
                    randomDirection(),
                    randomDirection(),
                    random(0.35f, 1.25f)
                )
            coords.add(coordVector)
        }
    }

    override fun draw() {
        updateLocations()
        updateSource()

        FILTER.process(sourceImage, filteredImage)

        when {
            mousePressed -> image(sourceImage)
            else -> image(filteredImage)
        }
    }

    private fun updateLocations(){
        coords.forEach { coordVector ->
            var x = coordVector.coord.x
            var y = coordVector.coord.y

            var xDirection  = coordVector.xDirection
            var yDirection  = coordVector.yDirection

            x += (coordVector.speed * xDirection)
            y += (coordVector.speed * yDirection)

            if (x >= width - BOUNDARY || x < BOUNDARY) xDirection *= -1
            if (y >= height - BOUNDARY || y < BOUNDARY) yDirection *= -1

            coordVector.coord.x = x
            coordVector.coord.y = y
            coordVector.xDirection = xDirection
            coordVector.yDirection = yDirection
        }
    }

    private fun updateSource(){
        sourceImage?.beginDraw()
        sourceImage?.background(color("#999999"))
        sourceImage?.noStroke()
        sourceImage?.lights()

        coords.forEach { coordVector ->
            sourceImage?.pushMatrix()
            sourceImage?.translate(coordVector.coord.x, coordVector.coord.y, 0f)
            sourceImage?.sphere(SPHERE_RAD)
            sourceImage?.popMatrix()
        }

        sourceImage?.endDraw()
    }

    private fun randomDirection(): Int{
        return when {
            random(1f) > .5f -> 1
            else -> -1
        }
    }
}