package prockt.sketches.archive

import prockt.KApplet
import prockt.api.KVector

/*

    Attracted to nearest neighbour

 */
class Sketch029: KApplet() {

    private val motes = mutableListOf<Mote>()

    private val count = 200

    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        repeat(count){
            motes.add(Mote())
        }
        noStroke()
        noCursor()
    }

    override fun draw() {
        motes.forEach { mote ->
            mote.update().draw()
            mote.draw()
        }

        fill(BLACK, 30)
        rect(0, 0, width, height)
    }

    override fun mousePressed() {
        motes.clear()
        repeat(count){
            motes.add(Mote())
        }
    }

    inner class Mote{
        private var location = KVector(random(width), random(height))
        private var velocity = KVector(0f, 0f)
        private var acceleration: KVector? = null
        private var maxSpeed = 3f
        var closestDistance = Float.MAX_VALUE

        fun update(): Mote {
            var closestMote: Mote? = null
            closestDistance = Float.MAX_VALUE
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
            val color = colorLerp("#9d2f4d", "#4973a1" , closestDistance, 20)
            fill(color)
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