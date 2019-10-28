package prockt.sketches.sk012

import processing.core.PGraphics
import processing.core.PImage
import prockt.KApplet
import processing.core.PVector
import prockt.OneBitFilter

/*

    Gravity logic derived from: https://www.openprocessing.org/sketch/113298

 */
class Sketch012: KApplet() {

    companion object {
        const val SKETCH_DIAM = 600
        const val MIN_MASS = 3f
        const val MAX_MASS = 5f
        const val MAX_VELOCITY = 0.01f
        const val MAX_FORCE = 0.000001f
        val FILTER = OneBitFilter.get("Atkinson").threshold(255)
    }

    var bodies = mutableListOf<Body>()
    private var sourceImage: PGraphics? = null
    private var filteredImage: PImage? = null

    override fun settings() {
        size(600, 600, P3D)
    }

    override fun setup() {
        sourceImage = createGraphics(SKETCH_DIAM, SKETCH_DIAM, P3D)
        filteredImage = PImage(SKETCH_DIAM, SKETCH_DIAM)
        bodies.clear()
        repeat(100){
            bodies.add(Body())
        }
        noStroke()
        fill(WHITE)
    }

    override fun mouseClicked() {
        setup()
    }


    override fun draw() {
        background(BLACK)

        updateSource()

        FILTER.process(sourceImage!!, filteredImage!!)

        when {
            mousePressed -> image(sourceImage!!)
            else -> image(filteredImage!!)
        }
    }

    fun updateSource(){
        sourceImage?.beginDraw()
        sourceImage?.background(BLACK)
        sourceImage?.noStroke()
        sourceImage?.lights()

        bodies.forEach { body ->
            body.draw()
        }

        sourceImage!!.endDraw()
    }

    inner class Body{

        var position= PVector(random(width.toFloat()), random(height.toFloat()))
        val velocity = PVector(random(-MAX_VELOCITY, MAX_VELOCITY), random(-MAX_VELOCITY, MAX_VELOCITY))
        var neighbour = PVector(0f, 0f)
        var mass = ceil(random(MIN_MASS, MAX_MASS))

        fun draw(){
            bodies.forEach { otherBody ->
                neighbour.set(otherBody.position)
                var dist = neighbour.dist(position)
                var force = (mass * otherBody.mass)/(sq(dist))
                if(force > MAX_FORCE) {
                    force = MAX_FORCE
                }
                neighbour.sub(position)
                neighbour.normalize()
                neighbour.mult(force / mass)
                velocity.add(neighbour)
                position.add(velocity)
            }
            sourceImage!!.pushMatrix()
            sourceImage!!.translate(position.x, position.y)
            sourceImage!!.sphere(10f * mass)
            sourceImage!!.popMatrix()
        }
    }
}
