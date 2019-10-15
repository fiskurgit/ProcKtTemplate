package processingkt

import processingkt.classloader.ContactSheet
import processingkt.classloader.SketchFinder
import processingkt.sketches.sk001.Sketch001
import processingkt.sketches.sk002.Sketch002
import processingkt.sketches.sk003.Sketch003
import processingkt.sketches.sk004.Sketch004
import java.awt.EventQueue
import java.util.*

class ProcessingKt {
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            when {
                args.isEmpty() -> {
                    println("Specify which sketch to run:")
                    val sketches = SketchFinder.getSketchNames()

                    if(sketches.isEmpty()){
                        displayWelcome()
                    }else{
                        displaySketches(sketches)
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
    }
}