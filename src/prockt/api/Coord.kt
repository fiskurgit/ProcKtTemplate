package prockt.api

import processing.core.PVector

data class Coord(var x: Float, var y: Float){

    companion object{
        fun fromVector(vector: PVector): Coord{
            return Coord(vector.x, vector.y)
        }

        fun toVector(coord: Coord): PVector {
            return PVector(coord.x, coord.y)
        }
    }

    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())

    fun dist(coord: Coord): Float {
        val dx = this.x - coord.x
        val dy = this.y - coord.y
        return Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
    }

    fun set(x: Number, y: Number){
        this.x = x.toFloat()
        this.y = y.toFloat()
    }
}