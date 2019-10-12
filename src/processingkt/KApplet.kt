package processingkt

import processingkt.gridutils.Grid
import processing.core.PApplet
import processing.event.KeyEvent
import processing.opengl.PJOGL
import java.awt.Color
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.JFileChooser

open class KApplet: PApplet() {

    val grid = Grid()
    private var commandKeyPressed = false
    private var saveScreenshotChooser: JFileChooser? = null

    companion object {
        val BLACK = Color.BLACK.rgb
        val WHITE = Color.WHITE.rgb
        const val NO_LOOP_TEMP_SCREENSHOT = "noloop_temp.png"
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

    open fun screenshot(){
        if(saveScreenshotChooser == null) saveScreenshotChooser = JFileChooser()
        val filename = "processingkt_${System.currentTimeMillis()}.png"
        saveScreenshotChooser?.selectedFile = File(filename)
        val returnVal = saveScreenshotChooser?.showSaveDialog(this.frame)
        if (returnVal == JFileChooser.APPROVE_OPTION){
            if(isLooping){
                saveFrame(saveScreenshotChooser?.selectedFile?.path)
            }else{
                val temp = File(NO_LOOP_TEMP_SCREENSHOT)
                if(temp.exists()){
                    Files.copy(Paths.get(temp.path), Paths.get(saveScreenshotChooser?.selectedFile!!.path))
                }
            }
        }
    }

    override fun noLoop() {
        saveFrame(NO_LOOP_TEMP_SCREENSHOT)
        super.noLoop()
    }

    override fun loop() {
        File(NO_LOOP_TEMP_SCREENSHOT).delete()
        super.loop()
    }
}