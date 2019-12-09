package prockt.sketches

import prockt.KApplet
import prockt.api.KVector


class Sketch034: KApplet() {
    val ballCount = 10
    val numberOfBands = 16
    val Band = 255/numberOfBands

    var balls = mutableListOf<Metaball>()

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
        repeat(ballCount){index ->
            balls[index].update()
        }

        loadPixels()
        repeat(width*height) {pixelIndex ->
            var col = 0f
            repeat(ballCount){index ->
                val y = floor(pixelIndex / width.toFloat())
                val x = pixelIndex % width
                val xx = balls[index].position.x - x
                val yy = balls[index].position.y - y
                col += balls[index].radius / sqrt(xx * xx + yy * yy)
            }
            col = (floor(((255 * col) / 255f) * numberOfBands) * Band).toFloat()
            pixels[pixelIndex] = color(col.toInt(), col.toInt(), col.toInt())
        }
        updatePixels()
    }


    inner class Metaball{

        var direction = KVector.randomDirection(this@Sketch034)
        val radius = random(4, 8)
        var position = KVector.randomPosition(this@Sketch034)

        fun update(){
            position.x += direction.x
            position.y += direction.y

            if (position.x + radius > width) direction.x *= -1
            if (position.x + radius < 0) direction.x *= -1
            if (position.y + radius > height) direction.y *= -1
            if (position.y + radius < 0) direction.y *= -1
        }

    }
}