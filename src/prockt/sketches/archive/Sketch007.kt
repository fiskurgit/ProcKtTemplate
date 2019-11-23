package prockt.sketches.archive

import prockt.KApplet
import prockt.api.Coord

/*

    Simple 2D stars/galaxy

 */
class Sketch007: KApplet() {

    private val origin = Coord(0, 0)
    private var radius = 5f
    private var count = 1000
    private var maxRadius = 300

    override fun setup() {
        noStroke()
    }

    override fun draw() {
        background(BLACK)

        while(radius < maxRadius) {
            fill(WHITE, 20)
            circle(Coord(width/2, height/2), (radius*2)/1.8)
            for (i in 0..count) {
                val coord = randomCircleCoord(radius)
                val dist = coord.dist(origin)
                val alpha = map(dist, 0, maxRadius, 255, 0)
                fill(WHITE, alpha)

                coord.x = coord.x + width / 2
                coord.y = coord.y + height / 2

                val size = map(dist, 0, maxRadius, 1, 7)

                circle(coord, size)
            }

            radius += 10
            count -= 20
        }

        overlay(WHITE, 40)

        noLoop()
    }

    override fun mousePressed() {
        radius = 5f
        count = 1000
        loop()
    }
}