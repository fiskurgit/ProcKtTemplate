package prockt.sketches

import processing.core.PShape
import prockt.KApplet

/*

    Given an array of 2D lines map onto surface of a sphere

 */
class Sketch021: KApplet() {

    private lateinit var mappedShape: PShape

    override fun settings() {
        mode(P3D)
    }

    override fun setup() {
        noFill()
        val lines = mutableListOf<Line>()

        lines.add(Line(0f, 0f, 0f, 100f))

        lines.add(Line(0f, 100f, 50f, 200f))
        lines.add(Line(0f, 100f, -50f, 200f))

        lines.add(Line(50f, 200f, 100f, 300f))
        lines.add(Line(-50f, 200f, -100f, 300f))

        mappedShape = createShape()
        mappedShape.beginShape(LINES)
        mappedShape.stroke(WHITE)

        val radius = 200
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

    override fun draw() {
        background(BLACK)
        camera(-width/2, -height/2, 150)

        rotateZ((TAU*frameCount)/500)

        stroke(WHITE, 10)
        sphere(195f)
        stroke(WHITE)
        shape(mappedShape)
    }
}