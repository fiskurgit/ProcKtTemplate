package prockt.sketches.archive

import processing.core.PGraphics
import processing.core.PImage
import processing.core.PShape
import prockt.KApplet
import prockt.OneBitFilter

class Sketch025: KApplet() {

    companion object{
        private val FILTER = OneBitFilter.get("Atkinson").threshold(255)
    }

    private var shape: PShape? = null
    private var sourceImage: PGraphics? = null
    private var filteredImage: PImage? = null

    override fun settings() {
        size(1000, 600, P3D)
    }

    override fun setup() {
        shape = generateShape()
        sourceImage = createGraphics(width, height, P3D)
        filteredImage = PImage(width, height)
    }

    override fun draw() {
        sourceImage?.beginDraw()
        sourceImage?.blendMode(ADD)
        sourceImage?.lights()
        sourceImage?.background(BLACK)
        sourceImage?.translate(width/2f, height/2f)

        sourceImage?.rotateY((TAU*frameCount)/1000)
        sourceImage?.rotateZ((TAU*frameCount)/1000)
        sourceImage?.rotateX((TAU*mouseX)/width)
        sourceImage?.shape(shape)
        sourceImage?.endDraw()

        FILTER.process(sourceImage, filteredImage)

        when {
            mousePressed -> image(sourceImage)
            else -> image(filteredImage)
        }
    }

    override fun mouseClicked() {
        shape = generateShape()
    }

    private fun generateShape(): PShape {
        val shape = createShape()
        shape.beginShape()
        shape.fill(WHITE, 120f)
        shape.noStroke()

        val vertices = random(10, 100)

        repeat(vertices){
            val coord = randomSphereCoord(200, 200)
            shape.vertex(coord.x, coord.y, coord.z)
        }

        shape.endShape(CLOSE)

        return shape
    }
}