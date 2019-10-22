package processingkt

import processing.core.PVector
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class Grid {

    var width = 0
    var height = 0

    var columns = 8
    var rows = 8

    var occupants = mutableListOf<Any>()

    fun draw(sketch: KApplet, color: Int = 200, alpha: Float = 100f){
        sketch.stroke(color, alpha)
        val cellWidth = cellWidth()
        val cellHeight = cellHeight()
        for (y in 0..rows) {
            sketch.dashedLine(0f, cellHeight * y, width.toFloat(), cellHeight * y, 3)
        }

        for (x in 0..columns) {
            sketch.dashedLine(cellWidth * x, 0f, cellWidth * x, height.toFloat(), 3)
        }
    }

    fun cellWidth(): Float{
        return width.toFloat()/columns
    }

    fun cellHeight(): Float{
        return height.toFloat()/rows
    }

    fun cellDiam(): Float{
        return Math.min(cellWidth(), cellHeight())
    }

    fun cellIndex(mouseX: Int, mouseY: Int): Int{
        val xC = mouseX / (width / columns)
        val yC = mouseY / (height / rows)
        return xC + columns * yC
    }

    fun cellOrigin(cellIndex: Int): PVector {
        val column = cellIndex%columns
        val row = cellIndex/rows
        val cellWidth = width/columns
        val cellHeight = height/rows
        val originX = (column * cellWidth) + cellWidth/2
        val originY = (row * cellHeight) + cellHeight/2
        return PVector(originX.toFloat(), originY.toFloat())
    }

    fun <T: Any>prepopulate(clazz: KClass<T>){
        val occupantCount = rows * columns
        for(index in 0..occupantCount){
            occupants.add(clazz.createInstance())
        }
    }

    fun <T: Any>prepopulate(rows: Int, columns: Int, clazz: KClass<T>){
        this.rows = rows
        this.columns = columns
        val occupantCount = rows * columns
        for(index in 0..occupantCount){
            occupants.add(clazz.createInstance())
        }
    }

    fun addOccupant(occupant: Any){
        occupants.add(occupant)
    }

    inline fun <reified T: Any>occupantAt(mouseX: Int, mouseY: Int): T{
        val index = cellIndex(mouseX, mouseY)
        return getOccupant(index)
    }

    inline fun <reified T: Any>occupants(): List<T>{
        return occupants.map { it as T }
    }

    inline fun <reified T: Any>getOccupant(cellIndex: Int): T{
        if(occupants.size <= cellIndex) throw Exception("Error: index $cellIndex out of bounds, we have ${occupants.size} occupants")
        return occupants[cellIndex] as T
    }

    fun setOccupant(cellIndex: Int, occupant: Any){
        if(occupants.size <= cellIndex) throw Exception("Error: index $cellIndex out of bounds, we have ${occupants.size} occupants")
        occupants[cellIndex] = occupant
    }

    fun count(): Int {
        return rows * columns
    }
}