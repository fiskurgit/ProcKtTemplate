package prockt.sketches.sk027

import prockt.KApplet
import prockt.api.KVector

//https://processing.org/tutorials/pvector/
class Sketch027: KApplet() {

    private val movers = mutableListOf<Mover>()

    override fun setup() {
        background(MOLNAR)

        repeat(30){
            movers.add(Mover())
        }
    }

    override fun draw() {
        background(MOLNAR)

        movers.forEach { mover ->
            mover.update()
            mover.checkEdges()
            mover.display()
        }
    }

    override fun mousePressed() {
        movers.clear()
        repeat(30){
            movers.add(Mover())
        }
    }

    inner class Mover{
        private var location = KVector(random(width), random(height))
        private var velocity = KVector(0f, 0f)
        private var acceleration: KVector? = null
        private var maxSpeed = 5f

        fun update() {
            val mouse = KVector(mouseX, mouseY)
            var dir = mouse - location
            dir.normalize()
            dir *= 0.1f
            acceleration = dir

            velocity += acceleration!!
            velocity.limit(maxSpeed)
            location += velocity
        }

        fun display() {
            stroke(0)
            fill(175)
            ellipse(location.x, location.y, 16, 16)
        }

        fun checkEdges() {
            when {
                location.x > width -> location.x = 0f
                location.x < 0 -> location.x = width.toFloat()
            }

            when {
                location.y > height -> location.y = 0f
                location.y < 0 -> location.y = height.toFloat()
            }
        }
    }
}