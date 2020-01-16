package prockt.api

import prockt.KApplet
import prockt.api.ray.Ray

interface Objekt {
    fun normal(coord: Coord? = null): KVector
    fun draw(kapl: KApplet)
    fun collision(ray: Ray): Coord?
    fun reflection(ray: Ray): Ray?
}