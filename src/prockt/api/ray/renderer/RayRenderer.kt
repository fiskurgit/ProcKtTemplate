package prockt.api.ray.renderer

import prockt.KApplet
import prockt.api.ray.Ray

interface RayRenderer {

    fun draw(kapl: KApplet, ray: Ray)
}