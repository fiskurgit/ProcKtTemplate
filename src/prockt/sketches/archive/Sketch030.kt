package prockt.sketches.archive

import prockt.KApplet
import prockt.api.KVector

/*

    Attracted to nearest neighbour, then breaking up after a while

 */
class Sketch030: KApplet() {

    private val motes = mutableListOf<Mote>()

    private val count = 200

    private val startColor = color("#9d2f4d")
    private val endColor = color("#4973a1")

    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        repeat(count){index ->
            motes.add(Mote(index))
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
        repeat(count){ index ->
            motes.add(Mote(index))
        }
    }

    inner class Mote(val id: Int){
        private var location = KVector(random(width), random(height))
        private var velocity = KVector(0f, 0f)
        private var acceleration: KVector? = null
        private var maxSpeed = 3f
        private var relationshipLength = random(100, 500)
        var closestDistance = Float.MAX_VALUE

        val exes = mutableListOf<Int>()

        var currentCompanion = -1
        var cyclesAttached = 0

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

            if(closestMote!!.id == currentCompanion){
                cyclesAttached++
            }
            currentCompanion = closestMote!!.id

            if(cyclesAttached > relationshipLength){
                exes.add(currentCompanion)

                currentCompanion = -1
                cyclesAttached = 0
                relationshipLength = random(100, 500)
            }

            directionToMote *= when {
                exes.contains(closestMote!!.id) -> -0.6f
                else -> 0.2f
            }

            acceleration = directionToMote

            velocity += acceleration!!
            velocity.limit(maxSpeed)
            location += velocity

            checkBounds()

            return this
        }

        fun draw() {
            val lerpAmount = map(closestDistance, 0, 20, 0f, 1f)
            fill(lerpColor(startColor, endColor , lerpAmount))
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