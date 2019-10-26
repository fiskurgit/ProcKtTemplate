package prockt.sketches.sk008

import processing.core.PGraphics
import processing.core.PImage
import prockt.KApplet
import prockt.OneBitFilter

class Sketch008 : KApplet() {

    companion object {
        const val DIAM = 600
        const val SPHERE_RAD = 40f
        const val SPHERE_COUNT = 170
    }

    data class CoordVector(var coord: Coord, var horizontalDirection: Int, var verticalDirection: Int, var speed: Float)

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

        for(i in 0..SPHERE_COUNT) {
            val coord = Coord(random(0f, DIAM.toFloat()),random(0f, DIAM.toFloat()))
            val horizontalDirection = when {
                random(1f) > .5f -> 1
                else -> -1
            }
            val verticalDirection = when {
                random(1f) > .5f -> 1
                else -> -1
            }
            val coordVector = CoordVector(coord, horizontalDirection, verticalDirection, random(1.25f, 2.5f))
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

        for(i in 0..SPHERE_COUNT){

            var coordVector = coords[i]

            var xpos = coordVector.coord.x
            var ypos = coordVector.coord.y

            var horizontalDirection  = coordVector.horizontalDirection
            var verticalDirection  = coordVector.verticalDirection

            xpos += (coordVector.speed * horizontalDirection)
            ypos += (coordVector.speed * verticalDirection)


            if (xpos >= width - SPHERE_RAD || xpos <= SPHERE_RAD) {
                horizontalDirection *= -1
            }
            if (ypos >= height - SPHERE_RAD || ypos <= SPHERE_RAD) {
                verticalDirection *= -1
            }

            coordVector.coord.x = xpos
            coordVector.coord.y = ypos
            coordVector.horizontalDirection = horizontalDirection
            coordVector.verticalDirection = verticalDirection

            coords.set(i, coordVector)

        }
    }

    private fun updateSource(){
        sourceImage?.beginDraw()
        sourceImage?.background(BLACK)

        sourceImage?.noStroke()
        sourceImage?.lights()

        for(i in 0..SPHERE_COUNT){
            sourceImage?.pushMatrix()
            val coordVector = coords[i]
            sourceImage?.translate(coordVector.coord.x, coordVector.coord.y, 0f)
            sourceImage?.sphere(SPHERE_RAD)
            sourceImage?.popMatrix()
        }

        sourceImage?.endDraw()
    }
}