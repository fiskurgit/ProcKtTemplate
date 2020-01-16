package prockt.api.ray.renderer

import prockt.KApplet
import prockt.KAppletApi.Companion.WHITE
import prockt.api.ray.Ray

class DefaultRenderer: RayRenderer {

    override fun draw(kapl: KApplet, ray: Ray) {
        kapl.stroke(WHITE, ray.alpha)
        kapl.line(ray.start, ray.end)
    }
}