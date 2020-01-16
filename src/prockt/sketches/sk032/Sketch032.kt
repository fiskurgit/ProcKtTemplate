package prockt.sketches.sk032

import prockt.KApplet
import prockt.api.Coord
import prockt.api.ray.Ray

class Sketch032: KApplet() {

    private val mirrorCount = 50
    private val mirrors = mutableListOf<MirrorObject>()

    private var maxRayLength: Float = 2000f

    override fun setup() {
        repeat(mirrorCount){
            mirrors.add( MirrorObject(Coord(random(width), random(height)), random(20f, 100f), radians(random(0f, 360f))))
        }
31
        maxRayLength = sqrt(sq(width.toFloat()) + sq(height.toFloat()))

        noCursor()
    }

    override fun draw() {
        background(BLACK)
        stroke(WHITE)

        val center = Coord(width/2, height/2)
        val mouse = Coord(mouseX, mouseY)
        val mouseBeam = Ray(center, mouse)

        mirrors.forEach { mirror ->
            mirror.draw(this)
        }

        run(mouseBeam)

        mouseBeam.draw(this, color(255, 50))

        fill(GREEN)
        circle(mouse, 8)

        fill(WHITE)
        circle(center, 5)

        noLoop()
    }

    override fun mouseMoved() {
        loop()
    }

    private fun run(ray: Ray?){
        if(ray == null) return
        run(processBeam(ray))
    }

    private fun processBeam(ray: Ray?): Ray?{
        if(ray == null) return null

        var closestMirror: MirrorObject?  = null
        var closestDistance = width * width * height.toFloat()
        var closestCollisionCoord: Coord? = null
        mirrors.forEach { mirror ->
            if(mirror != ray.originObject) {
                val collisionCoord = mirror.collision(ray)
                if (collisionCoord != null) {
                    val distance = ray.start.dist(collisionCoord)

                    if (distance < closestDistance) {
                        closestDistance = distance
                        closestMirror = mirror
                        closestCollisionCoord = collisionCoord
                    }
                }
            }
        }

        if(closestMirror != null){
            val collisionBeam = Ray(ray.start, closestCollisionCoord!!)
            collisionBeam.draw(this, MAGENTA)
        }

        val reflectBeam = closestMirror?.reflection(ray)
        reflectBeam?.setOrigin(closestMirror)

        if(reflectBeam == null){
            //Ray exits drawing area
            ray.draw(this, MAGENTA)

            var direction = ray.direction()
            direction *= maxRayLength
            val outOfBoundsBeam = Ray(ray.start, direction.coord())
            outOfBoundsBeam.draw(this, RED)
        }

        return reflectBeam
    }

    override fun keyPressed() {
        mirrors.clear()
        repeat(mirrorCount){
            mirrors.add(MirrorObject(Coord(random(width), random(height)), random(20f, 100f), radians(random(0f, 360f))))
        }
    }
}