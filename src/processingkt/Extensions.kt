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
