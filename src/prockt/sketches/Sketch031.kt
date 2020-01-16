package prockt.sketches

import prockt.KApplet
import prockt.api.KVector

/*

    Life, death, birth.
    todo: use of MutableIterator here would drop a couple of repeat iterations and allocations

 */
class Sketch031: KApplet() {

    private val motes = mutableListOf<Mote>()
    private val cycleOffspring = mutableListOf<Mote>()
    private val maxRelationshipMemory = 20
    private val count = 100
    private var offspring = 0
    private val startColor = color("#4973a1")
    private val endColor = color("#9d2f4d")
    private val coupledColor = WHITE

    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        noStroke()
        noCursor()
        repeat(count){ index ->
            motes.add(Mote(index))
        }
    }

    override fun draw() {

        motes.forEach { mote ->
            mote.update().draw()
            mote.draw()
        }

        motes.removeAll{ mote ->
            if(!mote.alive){
                fill(endColor)
                circle(mote.location, 12f)
            }
            !mote.alive
        }

        cycleOffspring.forEach { baby ->
            fill(startColor)
            circle(baby.location, 12f)
        }

        motes.addAll(cycleOffspring)
        cycleOffspring.clear()

        fill(BLACK, 30)
        rect(0, 0, width, height)
    }

    override fun mousePressed() {
        motes.clear()
        repeat(count){ index ->
            motes.add(Mote(index))
        }
    }

    inner class Mote(val id: Int, spawnLocation: KVector?, val parentId: Int?){

        constructor(id: Int) : this(id, null, null)

        var location: KVector = when {
            spawnLocation != null -> spawnLocation
            else -> KVector(random(width), random(height))
        }

        private var velocity = KVector(0f, 0f)
        private var acceleration: KVector? = null
        private var maxSpeed = 2.5f
        private var relationshipLength = random(400, 900)
        private var allowedCycles = random(900, 2000)
        private var closestDistance = Float.MAX_VALUE
        private val exes = mutableListOf<Int>()
        private var currentCompanion = -1
        private var cycles = 0
        private var cyclesAttached = 0
        var inRelationship = false
        var alive = true
        var hasOffspring = false

        fun update(): Mote {
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

            closestDistance = sortedDistances[1].second

            var directionToMote = closestMote!!.location - location
            directionToMote.normalize()

            //Is the closest mote the same as last cycle?
            if(currentCompanion == closestMote.id && closestDistance < 2.5){
                cyclesAttached++
            }

            inRelationship = cyclesAttached > 100


            //Update cloest mote id after we've checked if they were previous closest
            currentCompanion = closestMote.id

            //Have they been together too long?
            when {
                cyclesAttached > relationshipLength -> {
                    exes.add(currentCompanion)
                    currentCompanion = -1
                    cyclesAttached = 0
                    relationshipLength = random(100, 500)
                    inRelationship = false
                }
            }

            //If closest neighbour is an ex then move away,
            //if it's a parent or siblings move away quickly,
            //otherwise stay close to current companion
            directionToMote *= when {
                exes.contains(closestMote.id) -> -0.6f
                (parentId != null && (parentId == closestMote.id)) -> -2.4f
                (parentId != null && (parentId == closestMote.parentId)) -> -2.4f
                else -> 0.2f
            }

            acceleration = directionToMote

            //Block only applies to motes in a relationship
            if(inRelationship){
                //Find nearest single mote, index 0 is self, index 1 is partner
                var foundThreat = false
                var foundFriend = true//Should be false if block below is uncommented
                for(index in 2 until motes.size){
                    val mote = motes.find { mote ->
                        mote.id == sortedDistances[index].first
                    }

                    //Removed because I think it just makes couples coalesce
                    /*
                    if(mote!!.inRelationship && !foundFriend){
                        //Nearest neighbour in a relationship, move towards them for safety
                        val directionToFriend = mote.location - location
                        directionToFriend.normalise()
                        acceleration = directionToMote + (directionToFriend * 0.6f)
                        foundFriend = true
                    }
                    */

                    if(!mote!!.inRelationship && !foundThreat){
                        //Single - is a threat, move away
                        val directionToThreat = mote.location - location
                        directionToThreat.normalise()
                        acceleration = directionToMote + (directionToThreat * -0.4f)
                        foundThreat = true
                    }

                    if(foundThreat && foundFriend){
                        break
                    }
                }

                //Arbitary max population count
                if(!hasOffspring && cyclesAttached > relationshipLength/2 && motes.size < 200){
                    if(random(100) < 8){
                        hasOffspring = true
                        val numberOfOffspring = random(1, 2)
                        repeat(numberOfOffspring){
                            cycleOffspring.add(Mote(count + offspring, location, id))
                        }

                        offspring += numberOfOffspring
                    }
                }
            }

            velocity += acceleration!!

            if(inRelationship){
                val blackHole = KVector(width/2, height/2)
                var directionToBlackHole = blackHole - location
                directionToBlackHole.normalize()
                directionToBlackHole *= 0.08f
                velocity += directionToBlackHole

                velocity.limit(maxSpeed / 1.25f)
            }else{
                velocity.limit(maxSpeed)
            }

            location += velocity

            if (exes.size == maxRelationshipMemory) exes.removeAt(0)//Forget oldest relationships

            checkBounds()

            return this
        }

        fun draw() {
            if(inRelationship){
                fill(coupledColor)
            }else{
                fill(lerpColor(startColor, endColor ,  map(cycles, 0, allowedCycles, 0f, 1f)))
            }

            val diam = map(cycles, 0, allowedCycles, 2, 4.5)

            circle(location, diam)
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