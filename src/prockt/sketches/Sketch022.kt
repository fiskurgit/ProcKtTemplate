package prockt.sketches

import processing.core.PShape
import prockt.KApplet

/*

    Map random branching shape onto surface of a sphere (iteration 1)

 */
class Sketch022: KApplet() {

    private lateinit var mappedShape: PShape

    private var lines = mutableListOf<Line>()
    private var terminalLength = 0f
    private var randomAngleDiv = 0f
    private var reduction = 0f

    override fun settings() {
        mode(P3D)
        smooth(8)
    }

    override fun setup() {
        blendMode(ADD)
        generate()
    }

    override fun draw() {
        background(BLACK)

        camera(-width/2f, -height/2f, 8050f, 0f, 0f, 0f, 0.0f, 1.0f, 0.0f)
        rotateY((TAU*frameCount)/1000)//rotateY((TAU*mouseX)/width)
        rotateX((TAU*mouseY)/height)

        shape(mappedShape)
    }

    override fun mouseClicked() = generate()

    private fun generate(){
        lines.clear()
        terminalLength = random(0.2f, 1f)
        randomAngleDiv = random(2f, 10f)
        reduction = random(1.8f, 2.2f)
        val origin = Coord(width/2, height/2)
        val dendrites = random(1, 8)

        repeat(dendrites){ index ->
            val angleRnd = random(-1f, 1f)
            var angle = TAU/dendrites * index
            angle += TAU/dendrites
            grow(origin, 85f, angle + angleRnd)
        }

        mappedShape = createShape()
        mappedShape.beginShape(LINES)
        mappedShape.stroke(WHITE, 30f)
        mappedShape.strokeWeight(1f)

        val radius = 4000
        repeat(lines.size){index ->
            val line = lines[index]

            val x1 = radius * (sin(line.y1) * cos(line.x1))
            val y1 = radius * (sin(line.y1) * sin(line.x1))
            val z1 = radius * cos(line.y1)
            mappedShape.vertex(x1, y1, z1)


            val x2 = radius * (sin(line.y2) * cos(line.x2))
            val y2 = radius * (sin(line.y2) * sin(line.x2))
            val z2 = radius * cos(line.y2)
            mappedShape.vertex(x2, y2, z2)
        }

        mappedShape.endShape()
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
        offspringAngle = random(0f, 12f)//Not neccessary but...
        grow(Coord(0f, 0f), length / reduction, -offspringAngle)
        grow(Coord(length, 0f), length / reduction, -offspringAngle)
        popMatrix()
    }
}