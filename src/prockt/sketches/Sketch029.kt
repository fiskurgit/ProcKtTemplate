package prockt.sketches

import prockt.KApplet
import prockt.api.KVector

/*

    Attracted to nearest neighbour

 */

class Sketch029: KApplet() {

    private val motes = mutableListOf<Mote>()

    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        repeat(60){
            motes.add(Mote())
        }
        noStroke()
    }

    override fun draw() {
        motes.forEach { mote ->
            mote.update().draw()
            mote.draw()
        }

        fill(BLACK, 40)
        rect(0, 0, width, height)
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
            var closestMote: Mote? = null
            var closestDistance = Float.MAX_VALUE
            motes.forEach {mote ->
                val distance = location.distance(mote.location)
                if(distance > 1 && distance < closestDistance){
                    closestMote = mote
                    closestDistance = distance
                }
            }

            var directionToMote = closestMote!!.location - location

            directionToMote.normalize()
            directionToMote *= 0.2f

            acceleration = directionToMote

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