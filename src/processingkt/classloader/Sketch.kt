package processingkt.classloader

import processingkt.KApplet

data class Sketch(val name: String,
                  val clazz: Class<KApplet>)