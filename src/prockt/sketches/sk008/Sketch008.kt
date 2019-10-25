package prockt.sketches.sk008

import processing.core.PGraphics
import processing.core.PImage
import prockt.KApplet
import prockt.OneBitFilter

class Sketch008 : KApplet() {

    companion object {
        const val DIAM = 600
    }

    private var sourceImage: PGraphics? = null
    private var filteredImage: PImage? = null
    private val filter = OneBitFilter.get("NewspaperHalftone").threshold(255)

    override fun settings() {
        size(DIAM, DIAM, P3D)

    }

    override fun setup() {
        sourceImage = createGraphics(DIAM, DIAM, P3D)
        filteredImage = PImage(DIAM, DIAM)
        val fov = PI / 3.0f
        val cameraZ = (height / 2.0f) / tan(fov / 2.0f)
        sourceImage?.perspective(fov, width.toFloat() / height.toFloat(), cameraZ / 2.0f, cameraZ * 2.0f)

    }


    override fun draw() {
        sourceImage?.beginDraw()
        sourceImage?.background(BLACK)
        sourceImage?.noStroke()
        sourceImage?.lights()
        sourceImage?.camera(
            mouseX.toFloat(),
            height / 2f,
            (height / 2) / tan(PI / 6),
            width / 2f,
            height / 2f,
            0f,
            0f,
            1f,
            0f
        )
        sourceImage?.translate(width / 2f, height / 2f, 0f);
        sourceImage?.rotateY(map(mouseX,0, width, -TWO_PI, TWO_PI))
        sourceImage?.rotateX(map(mouseY,0, height, TWO_PI,-TWO_PI))
        sourceImage?.box(200f)
        sourceImage?.endDraw()
        filter.process(sourceImage!!, filteredImage!!)
        image(filteredImage!!)
    }
}