package p5kt

import p5kt.gridutils.Grid
import processing.core.PApplet
import processing.core.PConstants
import processing.event.KeyEvent
import processing.opengl.PJOGL
import java.awt.Color
import java.io.File
import javax.swing.JFileChooser

open class KApplet: PApplet() {

    val grid = Grid()
    private var commandKeyPressed = false

    companion object {
        val BLACK = Color.BLACK.rgb
        val WHITE = Color.WHITE.rgb
    }

    fun run() {
        PApplet.runSketch(arrayOf("P5Sketch"), this)
        surface.setTitle("p5kt.P5Kt Sketch")
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

    fun color(color: String): Int{
        return Color.decode(color).rgb
    }

    fun colorLerp(startColor: Int, endColor: Int, amount: Float): Int{
        return PApplet.lerpColor(startColor, endColor, amount, PConstants.RGB)
    }

    var saveScreenshotChooser: JFileChooser? = null

    open fun screenshot(){
        if(saveScreenshotChooser == null) saveScreenshotChooser = JFileChooser()
        val filename = "p5kt_${System.currentTimeMillis()}.png"
        saveScreenshotChooser?.selectedFile = File(filename)
        val returnVal = saveScreenshotChooser?.showSaveDialog(this.frame)
        if (returnVal == JFileChooser.APPROVE_OPTION) saveFrame(saveScreenshotChooser?.selectedFile?.path)
    }

    override fun keyPressed(e: KeyEvent?) {
        when {
            keyCode == 157 -> commandKeyPressed = true
            (key == 's' || key == 'S') && commandKeyPressed -> screenshot()
        }
        super.keyPressed(e)
    }

    override fun keyReleased() {
        commandKeyPressed = false
        super.keyReleased()
    }


}