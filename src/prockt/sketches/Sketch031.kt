package prockt.sketches

import prockt.KApplet
import prockt.api.KVector

/*

    Attracted to nearest neighbour, then breaking up after a while.
    While coupled, repulsed by nearest threat (mote that isn't in a couple)

 */
class Sketch031: KApplet() {

    private val motes = mutableListOf<Mote>()

    private val moteDiameter = 3f
    private val maxRelationshipMemory = 20
    private val count = 200

    private val startColor = color("#9d2f4d")
    private val endColor = color("#4973a1")

    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        repeat(count){ index ->
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

        fill(BLACK, 20)
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
        private var maxSpeed = 2f
        private var relationshipLength = random(100, 500)
        private var closestDistance = Float.MAX_VALUE
        private var closestThreatDistance = Float.MAX_VALUE
        private val exes = mutableListOf<Int>()
        private var currentCompanion = -1
        private var cyclesAttached = 0
        private var inRelationship = false

        fun update(): Mote{
            var closestMote: Mote? = null
            var closestThreat: Mote? = null
            closestDistance = Float.MAX_VALUE
            closestThreatDistance = Float.MAX_VALUE

            val distances = HashMap<Int, Float>()
            motes.forEach {mote ->
                val distance = location.distance(mote.location)
                distances[mote.id] = distance

                if(!mote.inRelationship && distance > 0 && distance < closestThreatDistance && distance > closestDistance){
                    closestThreat = mote
                    closestThreatDistance = distance
                }
            }

            val sortedDistances = distances.toList().sortedBy { (_, value) -> value}
            closestMote = motes[sortedDistances[1].first]
            closestDistance = sortedDistances[1].second

            var directionToMote = closestMote.location - location
            directionToMote.normalize()


            inRelationship = when (currentCompanion) {
                closestMote.id -> {
                    cyclesAttached++
                    true
                }
                else -> false
            }
            currentCompanion = closestMote.id

            when {
                cyclesAttached > relationshipLength -> {
                    exes.add(currentCompanion)
                    currentCompanion = -1
                    cyclesAttached = 0
                    relationshipLength = random(100, 500)
                }
            }

            directionToMote *= when {
                exes.contains(closestMote.id) -> -0.6f
                else -> 0.2f
            }

            acceleration = directionToMote

            if(inRelationship && closestThreat != null){
                val directionToThreat = closestThreat!!.location - location
                directionToThreat.normalise()
                acceleration = directionToMote + (directionToThreat * -0.2f)
            }

            velocity += acceleration!!
            velocity.limit(maxSpeed)
            location += velocity

            if (exes.size == maxRelationshipMemory) exes.removeAt(0)//Forget oldest relationships

            checkBounds()

            return this
        }

        fun draw() {
            val lerpAmount = map(closestDistance, 0, 20, 0f, 1f)
            fill(lerpColor(startColor, endColor , lerpAmount))
            circle(location, moteDiameter)
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