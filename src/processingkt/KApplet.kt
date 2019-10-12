package processingkt

import processingkt.gridutils.Grid
import processing.core.PApplet
import processing.event.KeyEvent
import processing.opengl.PJOGL
import java.awt.Color

open class KApplet: PApplet() {

    val grid = Grid()
    private var commandKeyPressed = false

    companion object {
        val BLACK = Color.BLACK.rgb
        val WHITE = Color.WHITE.rgb
    }

    fun run() {
        val sketchName = this::class.simpleName
        PApplet.runSketch(arrayOf(sketchName), this)
        surface.setTitle(sketchName)
    }

    override fun settings() {
        PJOGL.setIcon("processingkt.png")
        grid.width = width
        grid.height = height
    }

    override fun keyPressed(e: KeyEvent?) {
        when {
            keyCode == 157 -> commandKeyPressed = true//Apple Command key
            (key == 's' || key == 'S') && commandKeyPressed -> screenshot()
        }
        super.keyPressed(e)
    }

    override fun keyReleased() {
        commandKeyPressed = false
        super.keyReleased()
    }
}