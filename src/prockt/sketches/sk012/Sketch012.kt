package prockt.sketches.sk012

import prockt.KApplet
import processing.core.PVector

/*

    Gravity logic derived from: https://www.openprocessing.org/sketch/113298

 */
class Sketch012: KApplet() {

    var bodies = mutableListOf<Body>()
    var maxMass = 5f
    var fmax = 0.00001f
    var dist: Float = 0.toFloat()
    var f = 0f
    var maxVelocity = 0.01f


    override fun settings() {
        size(600, 600, P3D)
    }

    override fun setup() {
        repeat(100){
            bodies.add(Body())
        }
        noStroke()
        fill(WHITE)
    }


    override fun draw() {
        background(BLACK)
        lights()

        bodies.forEach { body ->
            body.draw()
        }
    }

    inner class Body{

        var position= PVector(random(width.toFloat()), random(height.toFloat()))
        val velocity = PVector(random(-maxVelocity, maxVelocity), random(-maxVelocity, maxVelocity))
        var neighbour= PVector(0f, 0f)
        var mass = ceil(random(maxMass))

        fun draw(){
            bodies.forEach { otherBody ->
                neighbour.set(otherBody.position)
                dist=neighbour.dist(position)
                f=(mass*otherBody.mass)/(sq(dist))
                if(f>fmax) {
                    f=fmax
                }
                neighbour.sub(position)
                neighbour.normalize()
                neighbour.mult(f/mass)
                velocity.add(neighbour)
                position.add(velocity)
            }
            pushMatrix()
            translate(position.x, position.y)
            sphere(5f * mass)
            popMatrix()
        }
    }
}
