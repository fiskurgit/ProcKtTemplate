package processingkt.sketches.sk001

import processingkt.KApplet
import processingkt.circle
import processing.core.PVector

class BasicCell {

    var active = false

    var opacity = 255f

    fun draw(kappl: KApplet, origin: PVector, diam: Float, color: Int){
        if(active){
            kappl.fill(color, opacity)
            kappl.noStroke()
            kappl.circle(origin.x, origin.y, diam)
            opacity -= 1f
            if(opacity < 0){
                opacity = 255f
                active = false
            }
        }
    }

    fun start() {
        opacity = 255f
        active = true
    }
}