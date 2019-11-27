package prockt.api

import processing.core.PApplet.sqrt
import processing.core.PVector

data class KVector(var x: Float, var y: Float) {

    constructor(x: Number, y: Number) : this(x.toFloat(), y.toFloat())
    constructor(coord: Coord) : this(coord.x, coord.y)

    companion object{
        fun fromPVector(pvector: PVector): KVector{
            return KVector(pvector.x, pvector.y)
        }

        fun dot(v1: KVector, v2: KVector): Float {
            return v1.x * v2.x + v1.y * v2.y
        }

        fun direction(start: KVector, end: KVector): KVector {
            return KVector(end.x - start.x, end.y - start.y)
        }

        fun normal(vectorA: KVector, vectorB: KVector): KVector{
            val delta = vectorA - vectorB
            delta.normalize()
            return KVector(-delta.y, delta.x)
        }

        fun empty(): KVector{
            return KVector(0, 0)
        }
    }


    fun reset(){
        x = 0f
        y = 0f
    }
    fun magnitude(): Float{
        return sqrt(x * x + y * y)
    }

    fun normalise(){
        val magnitude = magnitude()
        if(magnitude > 0){
            this.x = x/magnitude
            this.y = y/magnitude
        }
    }

    fun distance(other: KVector): Float{
        val dx = x - other.x
        val dy = y - other.y
        return sqrt(dx * dx + dy * dy)
    }

    fun direction(other: KVector): KVector {
        return KVector(other.x - x, other.y - y)
    }

    fun dot(other: KVector): Float {
        return x * other.x + y * other.y
    }

    fun perpendicular(): KVector {
        val pX = -y
        val pY = x
        return KVector(pX, pY)
    }

    fun normalize() = normalise()

    fun limit(max: Float){
        when {
            magnitudeSquared() > max * max -> {
                normalise()
                x *= max
                y *= max
            }
        }
    }

    fun magnitudeSquared(): Float {
        return x * x + y * y
    }

    fun coord(): Coord = Coord(x, y)

    operator fun plus(vector: KVector): KVector {
        return KVector(this.x + vector.x, this.y + vector.y)
    }

    operator fun minus(vector: KVector): KVector {
        return KVector(this.x - vector.x, this.y - vector.y)
    }

    operator fun times(value: Number): KVector {
        return KVector(this.x * value.toFloat(), this.y * value.toFloat())
    }

    operator fun div(value: Number): KVector {
        return KVector(this.x / value.toFloat(), this.y / value.toFloat())
    }

    fun toPVector(): PVector {
        return PVector(x, y)
    }

    fun clone(): KVector {
        return KVector(x, y)
    }
}