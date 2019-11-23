package prockt.sketches.archive

import prockt.KApplet
import prockt.api.Coord

/*

    Branching organic shape

 */
class Sketch020: KApplet() {

    private var lines = mutableListOf<Line>()
    private var terminalLength = 0f
    private var randomAngleDiv = 0f
    private var reduction = 0f

    override fun settings() {
        mode(P3D)
    }

    override fun setup() {
        strokeWeight(0.85f)
        stroke(EIGENGRAU, 20)
        generate()
    }

    override fun draw() {
        background(MOLNAR)

        repeat(lines.size){index ->
            lines[index].draw(this)
        }

        noLoop()
    }

    override fun mousePressed() {
        generate()
        loop()
    }

    private fun generate(){
        lines.clear()
        terminalLength = random(1f, 2f)
        randomAngleDiv = random(2f, 10f)
        reduction = random(1.6f, 1.9f)
        val origin = Coord(width/2, height/2)
        val dendrites = random(1, 8)

        repeat(dendrites){ index ->
            val angleRnd = random(-1f, 1f)
            var angle = TAU/dendrites * index
            angle += TAU/dendrites
            grow(origin, width/4f, angle + angleRnd)
        }

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