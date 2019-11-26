package prockt.api

import prockt.KApplet
import prockt.api.Coord
import prockt.api.KVector

interface Objekt {
    fun normal(coord: Coord? = null): KVector?
    fun draw(kapl: KApplet)
}