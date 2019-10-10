import grid.Grid
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import processing.opengl.PJOGL
import java.awt.Color

open class KApplet: PApplet() {

    companion object {
        val BLACK = Color.BLACK.rgb
        val WHITE = Color.WHITE.rgb

        fun e(error: String){
            kotlin.io.println("Error: $error")

        }
    }

    var grid = Grid()

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

    fun cellIndex(): Int{
        return grid.cellIndex(mouseX, mouseY)
    }

    fun cellOrigin(cellIndex: Int): PVector{
        return grid.cellOrigin(cellIndex)
    }

    fun drawGrid(){
        stroke(200, 100f)
        for (i in 0..grid.rows) {
            line(0f, grid.cellHeight() * i, width, grid.cellHeight() * i)
        }

        for (j in 0..grid.columns) {
            line(grid.cellWidth() * j, 0f, grid.cellWidth() * j, height)
        }
    }

    fun ellipse(x: Number, y: Number, w: Number, h: Number){
        ellipse(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat())
    }

    fun line(x1: Number, y1: Number, x2: Number, y2: Number){
        line(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat())
    }
}