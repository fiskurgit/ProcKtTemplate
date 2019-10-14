package processingkt

import java.awt.EventQueue
import java.io.File
import java.io.IOException
import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.JFileChooser
import java.io.PrintWriter
import java.awt.FileDialog
import javax.swing.JFrame

/*
    This is experimental
 */
object ContactSheet {

    private val screenshots = mutableListOf<Pair<String, String>>()
    private var chooser: JFileChooser? = null

    fun generate(){
        println("Warning: this feature is experimental. Only works with 2D sketches.")
        screenshots.clear()
        var currentSketch: KApplet? = null
        getClasses("processingkt").forEach {
            if(it.superclass.toString().endsWith("KApplet")){
                println("Found sketch: ${it.simpleName}")
                currentSketch = it.newInstance() as KApplet
                getScreenshot(currentSketch)
            }
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
        }
    }

    @Throws(ClassNotFoundException::class, IOException::class)
    private fun getClasses(packageName: String): Array<Class<*>> {
        val classLoader = Thread.currentThread().contextClassLoader!!
        val path = packageName.replace('.', '/')
        val resources = classLoader.getResources(path)
        val dirs = mutableListOf<File>()
        while (resources.hasMoreElements()) {
            val resource = resources.nextElement()
            dirs.add(File(resource.file))
        }
        val classes = mutableListOf<Class<*>>()
        for (directory in dirs) {
            findClasses(directory, packageName).forEach {
                classes.add(it)
            }
        }
        classes.sortBy { it.simpleName }.also {
            return classes.toTypedArray()
        }
    }

    @Throws(ClassNotFoundException::class)
    private fun findClasses(directory: File, packageName: String): List<Class<*>> {
        val classes = mutableListOf<Class<*>>()
        if (!directory.exists()) {
            return classes
        }
        val files = directory.listFiles()
        for (file in files!!) {
            if (file.isDirectory) {
                assert(!file.name.contains("."))
                findClasses(file, packageName + "." + file.name).forEach {
                    classes.add(it)
                }
            } else if (file.name.endsWith(".class")) {
                classes.add(Class.forName(packageName + '.'.toString() + file.name.substring(0, file.name.length - 6)))
            }
        }
        return classes
    }
}