package prockt.api.objekt

import prockt.api.Coord
import prockt.api.KVector

abstract class Objekt {

    abstract fun normal(coord: Coord?): KVector
    abstract fun draw()
}