package processingkt.sketches.sk004

import processing.core.PVector
import processingkt.KApplet
import processing.core.PConstants.TWO_PI

class SnowflakeCell {

    companion object {
        const val DENDRITE_ANGLE = TWO_PI / 6
    }
    private lateinit var kappl: KApplet
    private var dendriteReduce: Float = 1f
    private var dendriteEndLength: Float = 1f

    fun draw(kappl: KApplet, origin: PVector, diam: Float){
        this.kappl = kappl
        dendriteReduce = kappl.random(1.5f, 2.5f)
        dendriteEndLength = kappl.random(1f, 10f)

        val branches = 6
        for (i in 0 until branches) {
            val angle = DENDRITE_ANGLE * i
            generateDendrite(origin, diam/2, angle)
        }
    }

    private fun generateDendrite(origin: PVector, dendriteLength: Float, angle: Float) {


        if (dendriteLength < dendriteEndLength) {
            return
        }

        kappl.pushMatrix()
        kappl.translate(origin.x, origin.y)
        kappl.rotate(angle)

        generateDendrite(PVector(0f, 0f), dendriteLength / dendriteReduce, 0f)
        generateDendrite(PVector(dendriteLength, 0f), dendriteLength / dendriteReduce, 0f)
        generateDendrite(PVector(dendriteLength / dendriteReduce, 0f), dendriteLength / dendriteReduce, -DENDRITE_ANGLE)
        generateDendrite(PVector(dendriteLength / dendriteReduce, 0f), dendriteLength / dendriteReduce, DENDRITE_ANGLE)

        kappl.line(0f, 0f, dendriteLength, 0f)
        kappl.popMatrix()
    }
}