package prockt.sketches.sk033

import prockt.KApplet
import prockt.api.Coord
import prockt.api.Beam

class Sketch033: KApplet() {

    private val circlesCount = 15
    private val circles = mutableListOf<CircleObject>()

    private var maxRayLength: Float = 2000f

    override fun setup() {
        repeat(circlesCount){
            circles.add(CircleObject(Coord(random(width), random(height)), random(50f, 100f)))
        }

        maxRayLength = sqrt(sq(width.toFloat()) + sq(height.toFloat()))

        noCursor()
    }

    override fun draw() {
        background(BLACK)
        stroke(WHITE)

        val center = Coord(width/2, height/2)
        val mouse = Coord(mouseX, mouseY)
        val mouseBeam = Beam(center, mouse)

        circles.forEach { circle ->
            circle.draw(this)
        }


        mouseBeam.draw(this, color(255, 50))

        run(mouseBeam)



        fill(GREEN)
        circle(mouse, 8)

        fill(WHITE)
        circle(center, 5)

        noLoop()
    }

    override fun mouseMoved() {
        loop()
    }

    private fun run(beam: Beam?){
        if(beam == null) return

        val rBeam = processBeam(beam)
        run(rBeam)
    }

    private fun processBeam(beam: Beam?): Beam?{
        if(beam == null) return null

        var closestCircle: CircleObject?  = null
        var closestDistance = (width * width) * height.toFloat()
        var closestCollisionCoord: Coord? = null
        circles.forEach { circle ->
            if(circle != beam.originObject) {
                val collisionCoord = circle.collision(beam)
                if (collisionCoord != null) {
                    val distance = beam.start.dist(collisionCoord)
                    if (distance < closestDistance) {
                        closestDistance = distance
                        closestCircle = circle
                        closestCollisionCoord = collisionCoord.clone()
                    }
                }
            }
        }

        var reflectBeam: Beam? = null

        closestCollisionCoord?.let {coord ->
            closestCircle?.drawNormal(this, coord)
            val collisionBeam = Beam(beam.start, coord)
            collisionBeam.draw(this, YELLOW)

            fill(CYAN)
            noStroke()
            circle(coord, 10)

            reflectBeam = closestCircle?.reflection(beam)
            reflectBeam?.setOrigin(closestCircle)

        }

        if(reflectBeam == null){
            var direction = beam.direction()
            direction *= maxRayLength
            val outOfBoundsBeam = Beam(beam.start, direction.coord())
            outOfBoundsBeam.draw(this, RED)
        }

        return reflectBeam
    }

    override fun mouseClicked() {
        circles.clear()
        repeat(circlesCount){
            circles.add(CircleObject(Coord(random(width), random(height)), random(20f, 100f)))
        }
    }
}