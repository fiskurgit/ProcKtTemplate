package prockt.sketches

import prockt.KApplet
import prockt.api.KVector


class Sketch034: KApplet() {
    private val ballCount = 5
    private val numberOfBands = 8
    private var balls = mutableListOf<Metaball>()

    override fun settings() {
        size(350, 350)
        super.settings()
    }
    override fun setup() {
        repeat(ballCount){
            balls.add(Metaball())
        }
    }

    override fun draw() {
        loadPixels()
        repeat(pixels.size) {pixelIndex ->
            var col = 0f
            repeat(ballCount){index ->
                val y = floor(pixelIndex / width.toFloat())
                val x = pixelIndex % width
                val xx = balls[index].position.x - x
                val yy = balls[index].position.y - y
                col += balls[index].radius / sqrt(xx * xx + yy * yy)
            }
            val band = floor(col * numberOfBands) * (255/numberOfBands)
            pixels[pixelIndex] = color(band, band, band)
        }
        updatePixels()

        repeat(ballCount){ index ->
            balls[index].update()
        }
    }

    inner class Metaball{
        var direction = KVector.randomDirection(this@Sketch034)
        var position = KVector.randomPosition(this@Sketch034)
        val radius = random(6, 10)

        fun update(){
            position.x += direction.x
            position.y += direction.y

            if (position.x > Sketch035.PROCESS_DIAM || position.x < 0) direction.x *= -1
            if (position.y > Sketch035.PROCESS_DIAM || position.y < 0) direction.y *= -1
        }
    }
}