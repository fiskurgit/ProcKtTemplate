package prockt.classloader

import prockt.KApplet
import java.awt.EventQueue
import java.io.File
import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Paths
import java.io.PrintWriter
import java.awt.FileDialog
import javax.swing.JFrame

/*
    This is experimental
 */
object ContactSheet {

    private val screenshots = mutableListOf<Pair<String, String>>()

    fun generate(){
        println("Warning: this feature is experimental. Only works with 2D sketches.")
        screenshots.clear()

        SketchFinder.getSketches().forEach { sketch ->
            getScreenshot(sketch.newInstance())
        }

        chooseExportDirectory()
    }

    private fun getScreenshot(sketch: KApplet?) {
        if(sketch == null) return
        sketch.run()
        Thread.sleep(3000L)
        val sketchName = sketch::class.java.simpleName
        val filename = "contact_sheet_$sketchName.png"
        sketch.exportRasterHeadless(filename)
        sketch.stop()
        sketch.surface.setVisible(false)
        sketch.surface.stopThread()
        screenshots.add(Pair(sketchName, filename))
        Thread.sleep(1500L)
    }

    private fun chooseExportDirectory() {
        EventQueue.invokeAndWait{
            val frame = JFrame()
            System.setProperty("apple.awt.fileDialogForDirectories", "true")
            val fileDialog = FileDialog(frame)
            fileDialog.isVisible = true

            val directory = File("${fileDialog.directory}/${fileDialog.file}")

            if(!directory.exists()) directory.createNewFile()

            println("Export Contact Sheet to ${directory.absolutePath}")

            val markupBuilder = StringBuilder()

            markupBuilder.append("# Sketch Contact Sheet\n\n")

            screenshots.forEach { pair  ->
                val sketchName = pair.first
                val filename = pair.second

                markupBuilder.append("## $sketchName\n")
                markupBuilder.append("![$sketchName screenshot]($filename)\n\n")

                println("Copying file: $filename")
                val file = File(filename)
                if(file.exists()){
                    val exportFile = File("${directory.path}/$filename")
                    Files.copy(Paths.get(file.path), Paths.get(exportFile.path))
                }else{
                    println("File not found: $filename")
                }
            }

            PrintWriter("${directory.path}/contact_sheet.md").use { out -> out.println(markupBuilder.toString()) }
            println("Export finished.")
            System.exit(0)
        }
    }
}