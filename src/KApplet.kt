import gridutils.Grid
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import processing.opengl.PJOGL
import java.awt.Color
import kotlin.reflect.KClass

open class KApplet: PApplet() {

    val grid = Grid()

    companion object {
        val BLACK = Color.BLACK.rgb
        val WHITE = Color.WHITE.rgb

        fun e(error: String){
            kotlin.io.println("Error: $error")

        }
    }

    fun run() {
        val processingArgs = arrayOf("P5Sketch")
        PApplet.runSketch(processingArgs, this)
        surface.setTitle("P5Kt Sketch")
    }

    override fun settings() {
        size(600, 600, PConstants.P3D)
        PJOGL.setIcon("p5kt.png")
        grid.width = width
        grid.height = height
    }

    fun ellipse(x: Number, y: Number, w: Number, h: Number){
        ellipse(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat())
    }

    fun line(x1: Number, y1: Number, x2: Number, y2: Number){
        line(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat())
    }

    fun circle(x: Number, y: Number, diameter: Number){
        ellipse(x.toFloat(), y.toFloat(), diameter.toFloat(), diameter.toFloat())
    }
}