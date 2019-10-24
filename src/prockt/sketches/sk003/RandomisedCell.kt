package prockt.sketches.sk003

import processing.core.PVector
import prockt.*

class RandomisedCell {

    fun draw(kappl: KApplet, origin: PVector, diam: Float){
        val type = kappl.random(2)
        when(type){
            0 -> draw0(kappl, origin, diam)
            1 -> draw1(kappl, origin, diam)
            2 -> draw2(kappl, origin, diam)
        }
    }

    private fun draw0(kappl: KApplet, origin: PVector, diam: Float){
        val x = origin.x - (diam/2)
        val y = origin.y - (diam/2)
        val scale = kappl.random(1, 4)
        var bigDiam = diam * scale
        if (kappl.random(100) < 90) {
            bigDiam = diam
        }
        val iterations = kappl.randomWeightedSmall(6)
        val subDiv = (bigDiam / iterations).toInt().toFloat()
        for (i in 0 until iterations) {
            for (j in 0 until iterations) {
                kappl.circle(x + i * subDiv + subDiv / 2, y + j * subDiv + subDiv / 2, subDiv)
            }
        }
    }

    private fun draw1(kappl: KApplet, origin: PVector, diam: Float){
        //todo - another cell variant
    }

    private fun draw2(kappl: KApplet, origin: PVector, diam: Float){
        //todo - another cell variant
    }
}