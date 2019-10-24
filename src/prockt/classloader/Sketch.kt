package prockt.classloader

import prockt.KApplet

data class Sketch(val name: String,
                  val clazz: Class<KApplet>)