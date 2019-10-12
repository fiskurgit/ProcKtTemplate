package processingkt

import processingkt.gridutils.Grid
import processing.core.PApplet
import processing.event.KeyEvent
import processing.opengl.PJOGL
import java.awt.Color
import java.io.File
import javax.swing.JFileChooser

open class KApplet: PApplet() {

    val grid = Grid()
    private var commandKeyPressed = false
    private var saveScreenshotChooser: JFileChooser? = null

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

    open fun screenshot(){
        if(saveScreenshotChooser == null) saveScreenshotChooser = JFileChooser()
        val filename = "p5kt_${System.currentTimeMillis()}.png"
        saveScreenshotChooser?.selectedFile = File(filename)
        val returnVal = saveScreenshotChooser?.showSaveDialog(this.frame)
        if (returnVal == JFileChooser.APPROVE_OPTION) saveFrame(saveScreenshotChooser?.selectedFile?.path)
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