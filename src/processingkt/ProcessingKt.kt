package processingkt

import processingkt.sketches.sk001.Sketch001
import processingkt.sketches.sk002.Sketch002
import processingkt.sketches.sk003.Sketch003
import processingkt.sketches.sk004.Sketch004

class ProcessingKt {
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            /*
            when (args[0].toInt()) {
                1 -> Sketch001().run()
                2 -> Sketch002().run()
                3 -> Sketch003().run()
                4 -> Sketch004().run()
            }
            */

            ContactSheet.generate()
        }
    }
}