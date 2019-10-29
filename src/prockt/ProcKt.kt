package prockt

import prockt.classloader.ContactSheet
import prockt.classloader.SketchFinder
import java.awt.EventQueue
import java.io.File
import java.util.*

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
                            println("Specify which sketch to run:")
                            val sketches = SketchFinder.getSketchNames()

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

        private fun displaySketches(sketches: List<String>){
            sketches.forEachIndexed{ index: Int, name: String ->
                println("${index+1}: $name")
            }

            println("or 'cs' to generate a markdown contact sheet of all sketches.")

            val scanner = Scanner(System.`in`)
            val input = scanner.next()
            parse(input)
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
            println(" ____                 _  ___")
            println("|  _ \\ _ __ ___   ___| |/ / |_")
            println("| |_) | '__/ _ \\ / __| ' /| __|")
            println("|  __/| | | (_) | (__| . \\| |_")
            println("|_|   |_|  \\___/ \\___|_|\\_\\___|")
            println()

        }
    }
}