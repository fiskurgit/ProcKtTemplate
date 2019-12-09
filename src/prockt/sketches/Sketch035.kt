package prockt.sketches

import processing.core.PGraphics
import processing.core.PImage
import prockt.KApplet
import prockt.OneBitFilter
import prockt.api.KVector

class Sketch035: KApplet() {

    companion object{
        const val PROCESS_DIAM = 350
        const val SKETCH_SIZE = 800
    }

    private var sourceImage: PGraphics? = null
    private var filteredImage: PImage? = null

    private var filter = OneBitFilter.get("Atkinson").threshold(255)

    private val ballCount = 15
    private val numberOfBands = 6
    private val greyCoefficient = 255/numberOfBands
    private var balls = mutableListOf<Metaball>()

    override fun settings() {
        size(SKETCH_SIZE, SKETCH_SIZE, P3D)
        super.settings()
    }

    override fun setup() {
        sourceImage = createGraphics(PROCESS_DIAM, PROCESS_DIAM, P3D)
        filteredImage = PImage(PROCESS_DIAM, PROCESS_DIAM)

        repeat(ballCount){
            balls.add(Metaball())
        }
    }

    override fun keyPressed() {
        filter = OneBitFilter.getNext().threshold(255)
    }

    override fun draw() {
        sourceImage!!.loadPixels()
        repeat(sourceImage!!.pixels.size) {pixelIndex ->
            var sum = 0f
            repeat(ballCount){index ->
                val y = floor(pixelIndex / PROCESS_DIAM.toFloat())
                val x = pixelIndex % PROCESS_DIAM
                val xx = balls[index].position.x - x
                val yy = balls[index].position.y - y
                sum += balls[index].radius / sqrt(xx * xx + yy * yy)
            }
            val band = floor(sum * numberOfBands)
            val grey = band * greyCoefficient
            sourceImage!!.pixels[pixelIndex] = color(grey, grey, grey)
        }
        sourceImage!!.updatePixels()

        repeat(ballCount){ index ->
            balls[index].update()
        }

        filter.process(sourceImage, filteredImage)

        val scaleImage = filteredImage!!.copy()
        scaleImage.resize(SKETCH_SIZE, SKETCH_SIZE)
        image(scaleImage)
    }

    inner class Metaball{
        var direction = KVector.randomDirection(this@Sketch035)
        var velocity = random(1, 5)
        var position = KVector(random(0f, PROCESS_DIAM.toFloat()), random(0f, PROCESS_DIAM.toFloat()))
        val radius = random(2, 10)

        fun update(){
            position.x += (direction.x * velocity)
            position.y += (direction.y * velocity)

            if (position.x > PROCESS_DIAM || position.x < 0) direction.x *= -1
            if (position.y > PROCESS_DIAM || position.y < 0) direction.y *= -1
        }
    }
}