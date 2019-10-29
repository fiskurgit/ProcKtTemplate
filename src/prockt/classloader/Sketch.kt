package prockt.classloader

import prockt.KApplet

data class Sketch(val name: String,
                  val description: String?,
                  val clazz: Class<KApplet>)