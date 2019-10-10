import processing.core.PApplet
import processing.core.PConstants
import processing.opengl.PJOGL

open class KApplet: PApplet() {
    fun run() {
        val processingArgs = arrayOf("P5Sketch")
        PApplet.runSketch(processingArgs, this)
        surface.setTitle("P5Kt Sketch")
    }

    override fun settings() {
        size(600, 600, PConstants.P3D)
        PJOGL.setIcon("icon.png")
    }
}