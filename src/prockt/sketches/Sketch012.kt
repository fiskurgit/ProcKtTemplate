package prockt.sketches

import processing.core.PGraphics
import processing.core.PImage
import prockt.KApplet
import processing.core.PVector
import prockt.OneBitFilter

/*

    Gravity Balls
    logic derived from: https://www.openprocessing.org/sketch/113298

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
        size(
            SKETCH_DIAM,
            SKETCH_DIAM, P2D)
    }

    override fun setup() {
        sourceImage = createGraphics(
            SKETCH_DIAM,
            SKETCH_DIAM, P3D)
        filteredImage = PImage(
            SKETCH_DIAM,
            SKETCH_DIAM
        )
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
        updateSource()

        FILTER.process(sourceImage, filteredImage)

        when {
            mousePressed -> image(sourceImage)
            else -> image(filteredImage)
        }
    }

    private fun updateSource(){
        sourceImage?.beginDraw()
        sourceImage?.background(BLACK)
        sourceImage?.noStroke()
        sourceImage?.lights()

        bodies.forEach { body ->
            body.draw(sourceImage)
        }

        sourceImage?.endDraw()
    }

    inner class Body{

        private var position= PVector(random(width.toFloat()), random(height.toFloat()))
        private val velocity = PVector(random(-MAX_VELOCITY,
            MAX_VELOCITY
        ), random(-MAX_VELOCITY,
            MAX_VELOCITY
        ))
        private var neighbour = PVector(0f, 0f)
        private var mass = ceil(random(
            MIN_MASS,
            MAX_MASS
        ))

        fun draw(graphics: PGraphics?){
            bodies.forEach { otherBody ->
                neighbour.set(otherBody.position)
                val dist = neighbour.dist(position)
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
            graphics?.pushMatrix()
            graphics?.translate(position.x, position.y)
            graphics?.sphere(10f * mass)
            graphics?.popMatrix()
        }
    }
}
