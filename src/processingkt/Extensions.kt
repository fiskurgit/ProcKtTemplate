package processingkt

import processing.core.PApplet
import processing.core.PApplet.lerp
import processing.core.PConstants
import java.awt.Color
import processing.core.PVector
import sun.security.util.Length


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

fun KApplet.line(aCoord: PVector, bCoord: PVector){
    line(aCoord.x, aCoord.y, bCoord.x, bCoord.y)
}

fun KApplet.dashedLine(x1: Number, y1: Number, x2: Number, y2: Number, dashLength: Number){

    val v1 = PVector(x1.toFloat(), y1.toFloat())
    val v2 = PVector(x2.toFloat(), y2.toFloat())
    val distance = v1.dist(v2)

    val dashCount = distance/dashLength.toFloat()

    for (i in 0..dashCount.toInt()  step 3) {
        val xA = lerp(x1.toFloat(), x2.toFloat(), (i + 0.5f) / dashCount)
        val yA = lerp(y1.toFloat(), y2.toFloat(), (i + 0.5f) / dashCount)
        val aCoord = PVector(xA, yA)

        val xB = lerp(x1.toFloat(), x2.toFloat(), (i+1.5f) / dashCount)
        val yB = lerp(y1.toFloat(), y2.toFloat(), (i+1.5f) / dashCount)
        val bCoord = PVector(xB, yB)

        line(aCoord, bCoord)
    }
}

fun KApplet.dottedLine(x1: Number, y1: Number, x2: Number, y2: Number, gapLength: Number){

    val v1 = PVector(x1.toFloat(), y1.toFloat())
    val v2 = PVector(x2.toFloat(), y2.toFloat())
    val distance = v1.dist(v2)

    val dashCount = distance/gapLength.toFloat()

    for (i in 0..dashCount.toInt()) {
        val x = lerp(x1.toFloat(), x2.toFloat(), (i + 0.5f) / dashCount)
        val y = lerp(y1.toFloat(), y2.toFloat(), (i + 0.5f) / dashCount)
        point(x, y)
    }
}

fun KApplet.circleLine(x1: Number, y1: Number, x2: Number, y2: Number, gapLength: Number, circleDiam: Number){

    val v1 = PVector(x1.toFloat(), y1.toFloat())
    val v2 = PVector(x2.toFloat(), y2.toFloat())
    val distance = v1.dist(v2)

    val dashCount = distance/gapLength.toFloat()

    for (i in 0..dashCount.toInt()) {
        val x = lerp(x1.toFloat(), x2.toFloat(), (i + 0.5f) / dashCount)
        val y = lerp(y1.toFloat(), y2.toFloat(), (i + 0.5f) / dashCount)
        circle(x, y, circleDiam)
    }
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



//todo - there must be a better way
fun KApplet.randomWeightedLarge(max: Int): Int{
    val largeWeights = intArrayOf(90, 80, 80, 70, 70, 70, 60, 60, 60, 60, 50, 50, 50, 50, 50, 40, 40, 40, 40, 40, 40, 30, 30, 30, 30, 30, 30, 30, 20, 20, 20, 20, 20, 20, 20, 20, 10, 10, 10, 10, 10, 10, 10, 10, 10)
    val weights = largeWeights.size
    val weight = largeWeights[random(weights)]
    return ((random(max.toFloat()) /100.toFloat()) * weight).toInt()
}

//todo - there must be a better way
fun KApplet.randomWeightedSmall(max: Int): Int{
    val smallWeights = intArrayOf(10, 20, 20, 30, 30, 30, 40, 40, 40, 40, 50, 50, 50, 50, 50, 60, 60, 60, 60, 60, 60, 70, 70, 70, 70, 70, 70, 70, 80, 80, 80, 80, 80, 80, 80, 80, 90, 90, 90, 90, 90, 90, 90, 90, 90)
    val weights = smallWeights.size
    val weight = smallWeights[random(weights)]
    return ((random(max.toFloat()) /100.toFloat()) * weight).toInt()
}

fun KApplet.stroke(color: Int, alpha: Int){
    stroke(color, alpha.toFloat())
}


