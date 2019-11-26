package prockt.api

import prockt.KApplet

interface Objekt {
    fun normal(coord: Coord? = null): KVector
    fun draw(kapl: KApplet)
}