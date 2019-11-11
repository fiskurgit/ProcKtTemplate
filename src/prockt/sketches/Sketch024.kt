package prockt.sketches

import processing.core.PShape
import prockt.KApplet

/*

    Map random branching shape onto surface of a sphere X 2

 */
class Sketch024: KApplet() {

    private lateinit var mappedShapeA: PShape
    private lateinit var mappedShapeB: PShape

    private var lines = mutableListOf<Line>()
    private var terminalLength = 0f
    private var randomAngleDiv = 0f
    private var reduction = 0f
    private var drawPlanetMass = false

    override fun settings() {
        size(800, 800, P3D)
        smooth(8)
    }

    override fun setup() {
        blendMode(ADD)

        mappedShapeA = generate()
        mappedShapeB = generate()
    }

    override fun draw() {
        lights()
        background(EIGENGRAU)

        if(drawPlanetMass) {
            noStroke()
            fill(BLACK)
            sphere(3800f)
        }

        val fov = PI/3f
        val cameraZ = (height/2f) / tan(fov/2f)
        perspective(fov, 1f, cameraZ/10f, 14500f)
        camera(-width/2f, -height/2f, 10050f, 0f, 0f, 0f, 0.0f, 1.0f, 0.0f)

        pushMatrix()
        rotateZ((TAU*frameCount)/3000)//rotateY((TAU*mouseX)/width)
        rotateY((TAU*mouseY)/height)
        shape(mappedShapeA)
        popMatrix()

        pushMatrix()
        rotateY((TAU*frameCount)/2000 * -1)//rotateY((TAU*mouseX)/width)
        rotateX((TAU*mouseY)/height)
        shape(mappedShapeB)
        popMatrix()
    }

    override fun mouseClicked() {
        mappedShapeA = generate()
        mappedShapeB = generate()

    }

    override fun keyPressed() {
        drawPlanetMass = !drawPlanetMass
    }

    private fun generate(): PShape{
        lines.clear()
        terminalLength = random(0.2f, 1f)
        randomAngleDiv = random(2f, 10f)
        reduction = random(1.8f, 2.2f)
        val origin = Coord(width/2, height/2)
        val dendrites = random(1, 3)

        repeat(dendrites){ index ->
            val angleRnd = random(-1f, 1f)
            var angle = TAU/dendrites * index
            angle += TAU/dendrites
            grow(origin, 105f, angle + angleRnd)
        }

        val shape = createShape()
        shape.beginShape(LINES)

        shape.strokeWeight(0.75f)

        val radius = random(3900, 4100)
        repeat(lines.size){index ->
            val line = lines[index]

            val x1 = radius * (sin(line.y1) * cos(line.x1))
            val y1 = radius * (sin(line.y1) * sin(line.x1))
            val z1 = radius * cos(line.y1)
            val start = Particle(x1, y1, z1)

            val x2 = radius * (sin(line.y2) * cos(line.x2))
            val y2 = radius * (sin(line.y2) * sin(line.x2))
            val z2 = radius * cos(line.y2)
            val end = Particle(x2, y2, z2)

            if(start.distanceTo(end) < radius) {
                val color = colorLerp("#9d2f4d", "#4973a1", index, lines.size)
                shape.stroke(color, 125f)
                shape.vertex(x1, y1, z1)
                shape.vertex(x2, y2, z2)
            }
        }

        shape.endShape()
        return shape
    }

    private fun grow(origin: Coord, length: Float, angle: Float){
        when {
            length < terminalLength -> return
        }

        pushMatrix()
        translate(origin.x, origin.y)
        rotate(angle)
        lines.add(Line(screenX(0f, 0f), screenY(0f, 0f), screenX(length, 0f), screenY(length, 0f)))

        var offspringAngle = random(0f, 12f)//PI/6 or something for more order
        grow(Coord(length / reduction, 0f), length / reduction, -offspringAngle)
        grow(Coord(length / reduction, 0f), length / reduction, offspringAngle)

        offspringAngle = random(0f, 12f)
        grow(Coord(0f, 0f), length / reduction, -offspringAngle)
        grow(Coord(length, 0f), length / reduction, -offspringAngle)

        popMatrix()
    }
}