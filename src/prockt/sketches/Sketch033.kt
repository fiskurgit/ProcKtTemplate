package prockt.sketches

import prockt.KApplet
import prockt.api.Coord
import prockt.api.Beam
import prockt.sketches.sk032.MirrorObject

class Sketch033: KApplet() {

    private val mirrorCount = 50
    private val mirrors = mutableListOf<MirrorObject>()
    private lateinit var center: Coord
    private val light = color(255, 1)

    override fun settings() {
        mode(P3D)
        super.settings()
    }

    override fun setup() {
        repeat(mirrorCount){
            mirrors.add( MirrorObject(Coord(random(width), random(height)), random(20f, 100f), radians(random(0f, 360f))))
        }

        noCursor()
        background(BLACK)
        blendMode(ADD)
        center = Coord(width/2, height/2)
    }

    override fun draw() {
        repeat(10) {
            run(Beam(center, random(TWO_PI), width * height.toFloat()))
        }
    }

    private fun run(beam: Beam?){
        if(beam == null) return
        val reflection = processBeam(beam)
        run(reflection)
    }

    private fun processBeam(beam: Beam?): Beam?{
        if(beam == null) return null

        var closestMirror: MirrorObject?  = null
        var closestDistance = width * width * height.toFloat()
        var closestCollisionCoord: Coord? = null
        mirrors.forEach { mirror ->
            if(mirror != beam.originMirror) {
                val collisionCoord = mirror.collision(beam)
                if (collisionCoord != null) {
                    val distance = beam.start.dist(collisionCoord)

                    if (distance < closestDistance) {
                        closestDistance = distance
                        closestMirror = mirror
                        closestCollisionCoord = collisionCoord
                    }
                }
            }
        }

        if(closestMirror != null){
            val collisionBeam = Beam(beam.start, closestCollisionCoord!!)
            collisionBeam.draw(this, light)
        }

        val reflectBeam = closestMirror?.reflection(beam)
        reflectBeam?.setOrigin(closestMirror)

        if(reflectBeam == null){
            //Beam exits drawing area
            beam.draw(this, light)
        }

        return reflectBeam
    }

    override fun mouseClicked() {
        background(BLACK)
        mirrors.clear()
        repeat(mirrorCount){
            mirrors.add(MirrorObject(Coord(random(width), random(height)), random(20f, 100f), radians(random(0f, 360f))))
        }
    }
}