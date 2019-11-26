package prockt

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PImage
import processing.core.PVector
import prockt.api.Coord
import prockt.api.KVector
import java.awt.Color

open class KAppletApi: PApplet() {

    companion object {
        const val DEFAULT_SIZE = 600
        val BLACK = Color.BLACK.rgb
        val WHITE = Color.WHITE.rgb
        val RED = Color.RED.rgb
        val GREEN = Color.GREEN.rgb
        val MOLNAR = Color.decode("#E8E5E1").rgb
        val EIGENGRAU = Color.decode("#16161D").rgb
        val YELLOW = Color.decode("#FFFF00").rgb
        val MAGENTA = Color.decode("#FF00cc").rgb
        val CYAN = Color.decode("#00FFFF").rgb
    }

    //Sketch

    fun size(size: Int){
        size(size, size)
    }

    fun translate(x: Number, y: Number){
        translate(x.toFloat(), y.toFloat())
    }

    fun translate(vector: KVector){
        translate(vector.x, vector.y)
    }

    fun overlay(color: Int, alpha: Number){
        fill(color, alpha)
        rect(-1f, -1, width.toFloat() + 1, height.toFloat() + 1)
    }

    fun color(color: String): Int{
        return Color.decode(color).rgb
    }

    fun fill(color: String){
        fill(color(color))
    }

    fun fill(color: String, alpha: Number){
        fill(color(color), alpha.toFloat())
    }

    fun fill(color: Int, alpha: Number){
        fill(color, alpha.toFloat())
    }

    fun stroke(color: String){
        stroke(color(color))
    }

    fun stroke(color: Int, alpha: Number){
        stroke(color, alpha.toFloat())
    }

    fun colorLerp(startColor: Int, endColor: Int, amount: Float): Int{
        return lerpColor(startColor, endColor, amount, PConstants.RGB)
    }

    fun colorLerp(startColor: String, endColor: String, amount: Float): Int{
        return lerpColor(color(startColor), color(endColor), amount, PConstants.RGB)
    }

    fun colorLerp(startColor: String, endColor: String, index: Number, range: Number): Int{
        return lerpColor(color(startColor), color(endColor), index.toFloat()/range.toFloat(), PConstants.RGB)
    }

    fun camera(eyeX: Number, eyeY: Number, eyeZ: Number){
        camera(eyeX.toFloat(), eyeY.toFloat(), eyeZ.toFloat(), 0f, 0f, 0f, 0.0f, 1.0f, 0.0f)
    }

    //Drawing
    fun ellipse(x: Number, y: Number, w: Number, h: Number){
        ellipse(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat())
    }

    fun line(x1: Number, y1: Number, x2: Number, y2: Number){
        line(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat())
    }

    fun line(aCoord: Coord, bCoord: Coord){
        line(aCoord.x, aCoord.y, bCoord.x, bCoord.y)
    }

    fun line(aCoord: KVector, bCoord: KVector){
        line(aCoord.x, aCoord.y, bCoord.x, bCoord.y)
    }

    fun rect(x: Number, y: Number, width: Number, height: Number){
        rect(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
    }

    fun square(x: Number, y: Number, diameter: Number){
        rect(x, y, diameter, diameter)
    }

    fun circle(coord: Coord, diameter: Number){
        ellipse(coord.x, coord.y, diameter.toFloat(), diameter.toFloat())
    }

    fun circle(coord: KVector, diameter: Number){
        ellipse(coord.x, coord.y, diameter.toFloat(), diameter.toFloat())
    }

    fun circle(x: Number, y: Number, diameter: Number){
        ellipse(x.toFloat(), y.toFloat(), diameter.toFloat(), diameter.toFloat())
    }

    fun circle(diameter: Number){
        ellipse(0f, 0f, diameter.toFloat(), diameter.toFloat())
    }

    fun point(coord: Coord){
        point(coord.x, coord.y)
    }

    fun circleLine(x1: Number, y1: Number, x2: Number, y2: Number, gapLength: Number, circleDiam: Number){

        val v1 = Coord(x1.toFloat(), y1.toFloat())
        val v2 = Coord(x2.toFloat(), y2.toFloat())
        val distance = v1.dist(v2)

        val dashCount = distance/gapLength.toFloat()

        for (i in 0..dashCount.toInt()) {
            val x = lerp(x1.toFloat(), x2.toFloat(), (i + 0.5f) / dashCount)
            val y = lerp(y1.toFloat(), y2.toFloat(), (i + 0.5f) / dashCount)
            circle(x, y, circleDiam)
        }
    }

    /*
    For more control and performance use this lib instead: https://github.com/garciadelcastillo/-dashed-lines-for-processing-
 */
    fun dashedLine(x1: Number, y1: Number, x2: Number, y2: Number, dashLength: Number){

        val v1 = Coord(x1.toFloat(), y1.toFloat())
        val v2 = Coord(x2.toFloat(), y2.toFloat())
        val distance = v1.dist(v2)

        val dashCount = distance/dashLength.toFloat()

        for (i in 0..dashCount.toInt()  step 3) {
            val xA = lerp(x1.toFloat(), x2.toFloat(), (i + 0.5f) / dashCount)
            val yA = lerp(y1.toFloat(), y2.toFloat(), (i + 0.5f) / dashCount)
            val xB = lerp(x1.toFloat(), x2.toFloat(), (i + 1.5f) / dashCount)
            val yB = lerp(y1.toFloat(), y2.toFloat(), (i + 1.5f) / dashCount)

            line(xA, yA, xB, yB)
        }
    }

    fun dottedLine(x1: Number, y1: Number, x2: Number, y2: Number, gapLength: Number){

        val v1 = Coord(x1.toFloat(), y1.toFloat())
        val v2 = Coord(x2.toFloat(), y2.toFloat())
        val distance = v1.dist(v2)

        val dashCount = distance/gapLength.toFloat()

        for (i in 0..dashCount.toInt()) {
            val x = lerp(x1.toFloat(), x2.toFloat(), (i + 0.5f) / dashCount)
            val y = lerp(y1.toFloat(), y2.toFloat(), (i + 0.5f) / dashCount)
            point(x, y)
        }
    }

    fun image(image: PImage, x: Number, y: Number){
        image(image, x.toFloat(), y.toFloat())
    }

    fun image(image: PImage?){
        image(image, 0f, 0f)
    }

    //Maths

    fun map(value: Number, start1: Number, stop1: Number, start2: Number, stop2: Number): Float{
        return PApplet.map(value.toFloat(), start1.toFloat(), stop1.toFloat(), start2.toFloat(), stop2.toFloat())
    }

    fun random(): Float{
        return random(0f, 1f)
    }

    fun random(max: Int): Int{
        return random(max.toFloat()).toInt()
    }

    fun random(min: Int, max: Int): Int{
        return (random(min.toFloat(), max.toFloat()) + 0.5f).toInt()
    }

    //todo - there must be a better way
    fun randomWeightedLarge(max: Int): Int{
        val largeWeights = intArrayOf(90, 80, 80, 70, 70, 70, 60, 60, 60, 60, 50, 50, 50, 50, 50, 40, 40, 40, 40, 40, 40, 30, 30, 30, 30, 30, 30, 30, 20, 20, 20, 20, 20, 20, 20, 20, 10, 10, 10, 10, 10, 10, 10, 10, 10)
        val weights = largeWeights.size
        val weight = largeWeights[random(weights)]
        return ((random(max.toFloat()) /100.toFloat()) * weight).toInt()
    }

    //todo - there must be a better way
    fun randomWeightedSmall(max: Int): Int{
        val smallWeights = intArrayOf(10, 20, 20, 30, 30, 30, 40, 40, 40, 40, 50, 50, 50, 50, 50, 60, 60, 60, 60, 60, 60, 70, 70, 70, 70, 70, 70, 70, 80, 80, 80, 80, 80, 80, 80, 80, 90, 90, 90, 90, 90, 90, 90, 90, 90)
        val weights = smallWeights.size
        val weight = smallWeights[random(weights)]
        return ((random(max.toFloat()) /100.toFloat()) * weight).toInt()
    }

    //Geometry

    data class Line(var x1: Float, var y1: Float, var x2: Float, var y2: Float){
        fun draw(kapl: KApplet){
            kapl.line(x1, y1, x2, y2)
        }
    }

    data class Particle(var x: Float, var y: Float, var z: Float){
        fun distanceTo(other: Particle): Float{
            //todo - inline this for perf
            return PVector(x, y, z).dist(PVector(other.x, other.y, other.z))
        }
    }

    fun randomCircleCoord(radius: Number): Coord {
        val a = random(0f, 1f) * TWO_PI
        val r = radius.toFloat() * sqrt(random(0f, 1f))
        val x = r * cos(a)
        val y = r * sin(a)
        return Coord(x, y)
    }

    fun randomCircleCoord(innerRadius: Number, outerRadius: Number): Coord {
        val a = random(0f, 1f) * TWO_PI
        val r = sqrt(random(sq(innerRadius.toFloat()), sq(outerRadius.toFloat())))
        val x = r * cos(a)
        val y = r * sin(a)
        return Coord(x, y)
    }

    fun randomSphereCoord(radius: Number): Particle {
        var a = 0f
        var b = 0f
        var c = 0f
        var d = 0f
        var k = 99f
        while (k >= 1.0) {
            a = random(-1.0f, 1.0f)
            b = random(-1.0f, 1.0f)
            c = random(-1.0f, 1.0f)
            d = random(-1.0f, 1.0f)
            k = a * a + b * b + c * c + d * d
        }
        k /= radius.toFloat()
        val x = 2 * (b * d + a * c) / k
        val y = 2 * (c * d - a * b) / k
        val z = (a * a + d * d - b * b - c * c) / k
        return Particle(x, y, z)
    }

    fun randomSphereCoord(innerRadius: Number, outerRadius: Number): Particle {
        val radius = random(innerRadius.toFloat(), outerRadius.toFloat())
        return randomSphereCoord(radius)
    }

    //This isn't weighted, it'startVector a naive implementation where the count at a smaller radius means they're packed closer together
    //The visual effect is a weighted distribution so using the method name here, rather than the above method.
    fun randomCircleCoordWeighted(radius: Number): Coord {
        val a = random(0f, TWO_PI)
        val r = random(0f, radius.toFloat())
        val x = r * cos(a)
        val y = r * sin(a)
        return Coord(x, y)
    }

    fun randomCircleCoordWeighted(innerRadius: Number, outerRadius: Number): Coord {
        val a = random(0f, TWO_PI)
        val r = random(innerRadius.toFloat(), outerRadius.toFloat())
        val x = r * cos(a)
        val y = r * sin(a)
        return Coord(x, y)
    }
}