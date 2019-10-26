package prockt.sketches.sk008

import processing.core.PGraphics
import processing.core.PImage
import processing.core.PShape
import prockt.KApplet
import prockt.OneBitFilter

class Sketch008 : KApplet() {

    companion object {
        const val DIAM = 600
        const val SPHERE_RAD = 40f
        const val SPHERE_COUNT = 170
    }

    data class CoordVector(var coord: Coord, var xDirection: Int, var yDirection: Int, var speed: Float)

    private var sourceImage: PGraphics? = null
    private var filteredImage: PImage? = null
    private var filter = OneBitFilter.get("Sierra").threshold(255)
    private var coords = mutableListOf<CoordVector>()

    override fun settings() {
        size(DIAM, DIAM, P3D)
    }

    override fun setup() {
        sourceImage = createGraphics(DIAM, DIAM, P3D)
        filteredImage = PImage(DIAM, DIAM)

        repeat(SPHERE_COUNT) {
            val coord = Coord(random(0f, DIAM.toFloat()),random(0f, DIAM.toFloat()))
            val horizontalDirection = when {
                random(1f) > .5f -> 1
                else -> -1
            }
            val verticalDirection = when {
                random(1f) > .5f -> 1
                else -> -1
            }
            val coordVector = CoordVector(coord, horizontalDirection, verticalDirection, random(0.25f, 1f))
            coords.add(coordVector)
        }

    }

    override fun draw() {
        updateLocations()
        updateSource()
        filter.process(sourceImage!!, filteredImage!!)

        if(mousePressed) {
            image(sourceImage!!)
        }else{
            image(filteredImage!!)
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

            if (x >= width - SPHERE_RAD || x < SPHERE_RAD) xDirection *= -1
            if (y >= height - SPHERE_RAD || y < SPHERE_RAD) yDirection *= -1

            coordVector.coord.x = x
            coordVector.coord.y = y
            coordVector.xDirection = xDirection
            coordVector.yDirection = yDirection
        }
    }

    private fun updateSource(){
        sourceImage?.beginDraw()
        sourceImage?.background(BLACK)
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
}