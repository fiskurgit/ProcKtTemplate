package prockt.sketches.sk034

import processing.event.MouseEvent
import prockt.KApplet
import prockt.api.Coord
import prockt.api.ray.Ray
import prockt.api.ray.renderer.PointRenderer

class Sketch034: KApplet() {

    private val circlesCount = 15
    private val circles = mutableListOf<CircleObject>()

    private var maxRayLength: Float = 2000f
    private var rayRenderer = PointRenderer()

    override fun setup() {
        repeat(circlesCount){
            circles.add(CircleObject(Coord(random(width), random(height)), random(50f, 100f)))
        }

        maxRayLength = width.toFloat()

        noCursor()
        background(BLACK)
    }

    override fun draw() {
        stroke(WHITE, 2)

        val a = Coord(width/2 + 20, height/2 + 10)
        val b = Coord(width/2 - 10, height/2 - 20)
        val c = Coord(width/2, height/2)

        circles.forEach { circle ->
            circle.draw(this)
        }

        repeat(10) {
            run(Ray(a, random(TAU), maxRayLength / 3))
            run(Ray(b, random(TAU), maxRayLength / 3))
            run(Ray(c, random(TAU), maxRayLength / 3))
        }
    }

    override fun mouseClicked(event: MouseEvent?) {
        background(BLACK)
        circles.clear()
        repeat(circlesCount){
            circles.add(CircleObject(Coord(random(width), random(height)), random(50f, 100f)))
        }
    }

    private fun run(ray: Ray?){
        if(ray == null) return

        val rBeam = processBeam(ray)
        run(rBeam)
    }

    private fun processBeam(ray: Ray?): Ray?{
        if(ray == null) return null

        var closestCircle: CircleObject?  = null
        var closestDistance = (width * width) * height.toFloat()
        var closestCollisionCoord: Coord? = null
        circles.forEach { circle ->
            if(circle != ray.originObject) {
                val collisionCoord = circle.collision(ray)
                if (collisionCoord != null) {
                    val distance = ray.start.dist(collisionCoord)
                    if (distance < closestDistance) {
                        closestDistance = distance
                        closestCircle = circle
                        closestCollisionCoord = collisionCoord.clone()
                    }
                }
            }
        }

        var reflectRay: Ray? = null

        closestCollisionCoord?.let {coord ->
            val collisionBeam = Ray(ray.start, coord)
            collisionBeam.draw(this, rayRenderer)

            reflectRay = closestCircle?.reflection(ray)
            reflectRay?.setOrigin(closestCircle)

        }

        if(reflectRay == null){
            var direction = ray.direction()
            direction *= maxRayLength
            val outOfBoundsBeam = Ray(ray.start, direction.coord())
            outOfBoundsBeam.draw(this, rayRenderer)
        }

        return reflectRay
    }

    override fun mouseClicked() {
        circles.clear()
        repeat(circlesCount){
            circles.add(CircleObject(Coord(random(width), random(height)), random(20f, 100f)))
        }
    }
}