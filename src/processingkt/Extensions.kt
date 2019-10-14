package processingkt

import processing.core.PApplet
import processing.core.PConstants
import java.awt.Color

/*
    Only use extension functions for convenience overloads of existing Processing API
 */
fun KApplet.color(color: String): Int{
    return Color.decode(color).rgb
}

fun KApplet.ellipse(x: Number, y: Number, w: Number, h: Number){
    ellipse(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat())
}

fun KApplet.line(x1: Number, y1: Number, x2: Number, y2: Number){
    line(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat())
}

fun KApplet.circle(x: Number, y: Number, diameter: Number){
    ellipse(x.toFloat(), y.toFloat(), diameter.toFloat(), diameter.toFloat())
}

fun KApplet.colorLerp(startColor: Int, endColor: Int, amount: Float): Int{
    return PApplet.lerpColor(startColor, endColor, amount, PConstants.RGB)
}

fun KApplet.colorLerp(startColor: String, endColor: String, amount: Float): Int{
    return PApplet.lerpColor(color(startColor), color(endColor), amount, PConstants.RGB)
}

fun KApplet.colorLerp(startColor: String, endColor: String, index: Int, range: Int): Int{
    return PApplet.lerpColor(color(startColor), color(endColor), index/range.toFloat(), PConstants.RGB)
}

fun KApplet.random(max: Int): Int{
    return random(max.toFloat()).toInt()
}

fun KApplet.random(min: Int, max: Int): Int{
    return (random(min.toFloat(), max.toFloat()) + 0.5f).toInt()
}

//todo - this is crap
val largeWeights = intArrayOf(90, 80, 80, 70, 70, 70, 60, 60, 60, 60, 50, 50, 50, 50, 50, 40, 40, 40, 40, 40, 40, 30, 30, 30, 30, 30, 30, 30, 20, 20, 20, 20, 20, 20, 20, 20, 10, 10, 10, 10, 10, 10, 10, 10, 10)
fun KApplet.randomWeightedLarge(max: Int): Int{
    val weights = largeWeights.size
    val weight = largeWeights[random(weights)]
    return ((random(max.toFloat()) /100.toFloat()) * weight).toInt()
}


//todo - so is this
val smallWeights = intArrayOf(10, 20, 20, 30, 30, 30, 40, 40, 40, 40, 50, 50, 50, 50, 50, 60, 60, 60, 60, 60, 60, 70, 70, 70, 70, 70, 70, 70, 80, 80, 80, 80, 80, 80, 80, 80, 90, 90, 90, 90, 90, 90, 90, 90, 90)
fun KApplet.randomWeightedSmall(max: Int): Int{
    val weights = smallWeights.size
    val weight = smallWeights[random(weights)]
    return ((random(max.toFloat()) /100.toFloat()) * weight).toInt()
}

fun KApplet.stroke(color: Int, alpha: Int){
    stroke(color, alpha.toFloat())
}


