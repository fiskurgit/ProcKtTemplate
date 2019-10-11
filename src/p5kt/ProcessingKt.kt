package p5kt

import p5kt.sketches.sk001.Sketch001
import p5kt.sketches.sk002.Sketch002

class ProcessingKt {
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            when (args[0].toInt()) {
                1 -> Sketch001().run()
                2 -> Sketch002().run()
            }
        }
    }
}