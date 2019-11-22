package prockt

import prockt.classloader.ContactSheet
import prockt.classloader.Sketch
import prockt.classloader.SketchFinder
import java.awt.EventQueue
import java.io.File
import java.util.*
import kotlin.system.exitProcess

/*

 ____                 _  ___
|  _ \ _ __ ___   ___| |/ / |_
| |_) | '__/ _ \ / __| ' /| __|
|  __/| | | (_) | (__| . \| |_
|_|   |_|  \___/ \___|_|\_\___|

 */
class ProcKt {
    companion object {
        private const val IDE_TEMP_FILE = ".processingkt_sketch_launch"

        @JvmStatic
        fun main(args: Array<String>) {
            printLogo()
            when {
                args.isEmpty() -> {
                    val ideTempFile = File(IDE_TEMP_FILE)
                    when {
                        ideTempFile.exists() -> {
                            val sketchClazz = ideTempFile.readText().trim()
                            println("Launching $sketchClazz")
                            val sketch = SketchFinder.getSketch(sketchClazz)
                            if(sketch == null){
                                println("$sketchClazz not found")
                            }else {
                                sketch.run()
                                EventQueue.invokeAndWait {
                                    sketch.surface?.setAlwaysOnTop(true)
                                }
                            }
                            ideTempFile.delete()
                        }
                        else -> {
                            println()
                            println("Select a sketch:")
                            println()
                            val sketches = SketchFinder.getSketchesFull()

                            if (sketches.isEmpty()) {
                                displayWelcome()
                            } else {
                                displaySketches(sketches)
                            }
                        }
                    }
                }
                else -> parse(args[0])
            }
        }

        private fun displayWelcome(){
            println("Create a new kotlin file that subclasses KApplet to get started (new file wizard coming soon hopefully)")
        }

        private fun displaySketches(sketches: List<Sketch>){
            sketches.forEachIndexed{ index: Int, sketch: Sketch ->
                val numberPrefix = "%02d".format(index+1)
                when {

                    sketch.description != null -> {
                        println("$numberPrefix: ${sketch.name} - ${sketch.description}")
                    }
                    else -> {
                        println("$numberPrefix: ${sketch.name}")
                    }
                }

            }

            println("or 'cs' to generate a markdown contact sheet of all sketches.")

            val scanner = Scanner(System.`in`)
            when (val input = scanner.next()) {
                "cs" -> parse(input)
                else -> {
                    val index = input.toIntOrNull()
                    when {
                        index != null -> sketches[(input.toInt()-1)].clazz.newInstance().run()
                        else -> {
                            println("Invalid input: $input")
                            exitProcess(-1)
                        }
                    }
                }
            }
        }

        private fun parse(arg: String){
            val index = arg.toIntOrNull()

            when {
                index != null ->{
                    val sketch = SketchFinder.getSketchAt(index-1)
                    sketch.run()
                    EventQueue.invokeAndWait {
                        sketch.surface?.setAlwaysOnTop(true)
                    }
                }
                else -> if(arg == "cs" || arg == "contact_sheet"){
                    ContactSheet.generate()
                }
            }
        }

        private fun printLogo(){
            println()
            Terminal.lCyan(" ____                 _  ___")
            Terminal.lCyan("|  _ \\ _ __ ___   ___| |/ / |_")
            Terminal.lCyan("| |_) | '__/ _ \\ / __| ' /| __|")
            Terminal.lCyan("|  __/| | | (_) | (__| . \\| |_")
            Terminal.lCyan("|_|   |_|  \\___/ \\___|_|\\_\\___|")
            println()
        }
    }
}