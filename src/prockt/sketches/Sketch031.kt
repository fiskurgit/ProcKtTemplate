package prockt.sketches

import prockt.KApplet
import prockt.api.KVector

/*

    Life, death, birth.

 */
class Sketch031: KApplet() {

    private val motes = mutableListOf<Mote>()
    private val cycleOffspring = mutableListOf<Mote>()

    private val moteDiameter = 3f
    private val maxRelationshipMemory = 20
    private val count = 200
    private var offspring = 0

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

        motes.removeAll{ mote ->
            !mote.alive
        }

        motes.addAll(cycleOffspring)
        cycleOffspring.clear()

        fill(BLACK, 20)
        rect(0, 0, width, height)
    }

    override fun mousePressed() {
        motes.clear()
        repeat(count){ index ->
            motes.add(Mote(index))
        }
    }

    inner class Mote(val id: Int, spawnLocation: KVector?){

        constructor(id: Int) : this(id, null)

        private var location: KVector = when {
            spawnLocation != null -> spawnLocation
            else -> KVector(random(width), random(height))
        }

        private var velocity = KVector(0f, 0f)
        private var acceleration: KVector? = null
        private var maxSpeed = 2f
        private var relationshipLength = random(100, 500)
        private var allowedCycles = random(500, 1000)
        private var closestDistance = Float.MAX_VALUE
        private val exes = mutableListOf<Int>()
        private var currentCompanion = -1
        private var cycles = 0
        private var cyclesAttached = 0
        private var inRelationship = false
        var alive = true
        var hasOffspring = false

        fun update(): Mote{
            if(motes.size == 1) return this
            cycles++
            if(cycles == allowedCycles){
                //die
                alive = false
            }
            //Get distances of all motes
            val distances = HashMap<Int, Float>()
            motes.forEach {mote ->
                val distance = location.distance(mote.location)
                distances[mote.id] = distance
            }

            //Sort - self will be index 0
            val sortedDistances = distances.toList().sortedBy { (_, value) -> value}

            //Closest neighbour
            val closestMote = motes.find { mote ->
                mote.id == sortedDistances[1].first
            }

            //and their distance value
            closestDistance = sortedDistances[1].second

            var directionToMote = closestMote!!.location - location
            directionToMote.normalize()

            //Is the closest mote the same as last cycle?
            inRelationship = when (currentCompanion) {
                closestMote.id -> {
                    cyclesAttached++
                    true
                }
                else -> false
            }

            //Update cloest mote id after we've checked if they were previous cloests
            currentCompanion = closestMote.id

            //Have they been together too long?
            when {
                cyclesAttached > relationshipLength -> {
                    exes.add(currentCompanion)
                    currentCompanion = -1
                    cyclesAttached = 0
                    relationshipLength = random(100, 500)
                }
            }

            //If closest neighbour is an ex then move away, otherwise stay close to current companion
            directionToMote *= when {
                exes.contains(closestMote.id) -> -0.6f
                else -> 0.2f
            }

            acceleration = directionToMote

            //Block only applies to motes in a relationship
            if(inRelationship){
                //Find nearest single mote, index 0 is self, index 1 is partner
                var foundThreat = false
                var foundFriend = false
                for(index in 2 until motes.size){
                    val mote = motes.find { mote ->
                        mote.id == sortedDistances[index].first
                    }
                    if(mote!!.inRelationship && !foundFriend){
                        //Nearest neighbour in a relationship, move towards them for safety
                        val directionToFriend = mote.location - location
                        directionToFriend.normalise()
                        acceleration = directionToMote + (directionToFriend * 0.6f)
                        foundFriend = true
                    }
                    if(!mote.inRelationship && !foundThreat){
                        //Single - is a threat, move away
                        val directionToThreat = mote.location - location
                        directionToThreat.normalise()
                        acceleration = directionToMote + (directionToThreat * -0.3f)
                        foundThreat = true
                    }

                    if(foundThreat && foundFriend){
                        break
                    }
                }

                if(!hasOffspring && cyclesAttached > relationshipLength/2){
                    if(random(100) > 98){
                        hasOffspring = true
                        cycleOffspring.add(Mote(count + offspring, location))
                        offspring++
                    }
                }
            }

            velocity += acceleration!!
            velocity.limit(maxSpeed)
            location += velocity

            if (exes.size == maxRelationshipMemory) exes.removeAt(0)//Forget oldest relationships

            checkBounds()

            return this
        }

        fun draw() {
            val lerpAmount = map(closestDistance, 0, 10, 0f, 1f)
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