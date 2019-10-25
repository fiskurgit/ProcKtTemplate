package prockt.sketches.sk007

import prockt.KApplet

class Sketch007: KApplet() {

    private val origin = Coord(0, 0)
    private var radius = 10f
    private var count = 600
    private var maxRadius: Float = 0f

    override fun setup() {
        noStroke()
        fill(WHITE, 150)
        maxRadius = width/2.2f
        super.setup()
    }

    override fun draw() {
        background(BLACK)

        while(radius < maxRadius) {

            for (i in 0..count) {
                val coord = randomCircleCoord(radius)
                val dist = coord.dist(origin)
                val color = colorLerp("#ffffff", "#ff0000", dist, radius)
                val alpha = map(dist, 0, radius, 255, 0)
                fill(color, alpha)

                coord.x = coord.x + width / 2
                coord.y = coord.y + height / 2

                val size = map(dist, 0, radius, 1, 8)

                circle(coord, size)
            }

            radius += 5
            count -= 10
        }

        fill(255, 40)
        rect(0f, 0f, width.toFloat(), height.toFloat())

        noLoop()
    }

    override fun mousePressed() {
        radius = 10f
        count = 600
        loop()
    }
}