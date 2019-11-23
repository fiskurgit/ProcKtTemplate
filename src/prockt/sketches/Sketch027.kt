package prockt.sketches

import prockt.KApplet
import prockt.api.KVector

/*

    KVector beginnings - trapped in orbit, but trying to be alone.
    See: https://processing.org/tutorials/pvector/

 */

class Sketch027: KApplet() {

    private val motes = mutableListOf<Mote>()

    override fun setup() {
        repeat(30){
            motes.add(Mote())
        }
        noStroke()
    }

    override fun draw() {
        background(EIGENGRAU)

        fill(WHITE, 200)
        circle(width/2, height/2, 30)

        motes.forEach { mote ->
            mote.update().draw()
            mote.draw()
        }
    }

    override fun mousePressed() {
        motes.clear()
        repeat(60){
            motes.add(Mote())
        }
    }

    inner class Mote{
        private var location = KVector(random(width), random(height))
        private var velocity = KVector(0f, 0f)
        private var acceleration: KVector? = null
        private var maxSpeed = 5f

        fun update(): Mote{
            var agg = KVector(0, 0)
            motes.forEach {mote ->
                var directionToMote = mote.location - location

                directionToMote.normalize()
                directionToMote *= -0.1f
                agg += directionToMote
            }

            val blackHole = KVector(width/2, height/2)
            var directionToBlackHole = blackHole - location
            directionToBlackHole.normalize()
            directionToBlackHole *= 2.4f
            agg += directionToBlackHole

            acceleration = agg/motes.size

            velocity += acceleration!!
            velocity.limit(maxSpeed)
            location += velocity

            checkBounds()

            return this
        }

        fun draw() {
            fill(MOLNAR)
            ellipse(location.x, location.y, 4, 4)
        }

        private fun checkBounds() {
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