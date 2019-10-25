package prockt

import processing.core.PApplet
import processing.core.PGraphics
import processing.event.KeyEvent
import processing.opengl.PJOGL
import java.awt.Color
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.JFileChooser

open class KApplet: KAppletApi() {

    val grid = Grid()
    private var commandKeyPressed = false
    private var saveChooser: JFileChooser? = null

    companion object {
        const val NO_LOOP_TEMP_SCREENSHOT = "noloop_temp.png"
        const val VECTOR_TEMP_FILE = "vector_temp.pdf"
    }

    fun run() {
        val sketchName = this::class.simpleName
        PApplet.runSketch(arrayOf(sketchName), this)
        surface.setTitle(sketchName)
    }

    override fun settings() {
        //Override default 100x100 sketch size
        when {
            width == 100 && height == 100 -> size(DEFAULT_SIZE)
        }
        PJOGL.setIcon("prockt.png")
        grid.width = width
        grid.height = height

        super.settings()
    }

    override fun keyPressed(e: KeyEvent?) {
        when {
            keyCode == 157 -> commandKeyPressed = true//Apple Command key
            (key == 's' || key == 'S') && commandKeyPressed -> exportRaster()
            (key == 'p' || key == 'P') && commandKeyPressed -> exportVector()
        }
        super.keyPressed(e)
    }

    override fun keyReleased() {
        commandKeyPressed = false
        super.keyReleased()
    }

    open fun exportRaster(){
        if(saveChooser == null) saveChooser = JFileChooser()
        val filename = "processingkt_raster_${System.currentTimeMillis()}.png"
        saveChooser?.selectedFile = File(filename)
        val returnVal = saveChooser?.showSaveDialog(this.frame)
        if (returnVal == JFileChooser.APPROVE_OPTION){
            if(isLooping){
                saveFrame(saveChooser?.selectedFile?.path)
            }else{
                val temp = File(NO_LOOP_TEMP_SCREENSHOT)
                if(temp.exists()) Files.copy(Paths.get(temp.path), Paths.get(saveChooser?.selectedFile!!.path))
            }
        }
    }

    open fun exportRasterHeadless(filename: String){
        val file = File(filename)
        if(file.exists()) file.delete()
        if(isLooping){
            saveFrame(filename)
        }else{
            val temp = File(NO_LOOP_TEMP_SCREENSHOT)
            if(temp.exists()) Files.copy(Paths.get(temp.path), Paths.get(filename))
        }
    }

    open fun exportVector(){
        val temp = File(VECTOR_TEMP_FILE)
        if(temp.exists()) {
            if (saveChooser == null) saveChooser = JFileChooser()
            val filename = "processingkt_vector_${System.currentTimeMillis()}.pdf"
            saveChooser?.selectedFile = File(filename)
            val returnVal = saveChooser?.showSaveDialog(this.frame)
            if (returnVal == JFileChooser.APPROVE_OPTION) Files.copy(Paths.get(temp.path), Paths.get(saveChooser?.selectedFile!!.path))
        }else{
            println("No PDF export available, have you added startPdf() and endPdf()?")
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

    fun startPdf(): PGraphics = beginRecord(PDF, VECTOR_TEMP_FILE)
    fun endPdf() = endRecord()
}