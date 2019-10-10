package grid

import processing.core.PVector

class Grid {

    var width = 0
    var height = 0

    var columns = 10
    var rows = 10

    var occupants = mutableListOf<Any>()

    fun cellWidth(): Float{
        return width.toFloat()/columns
    }

    fun cellHeight(): Float{
        return height.toFloat()/rows
    }

    fun cellIndex(mouseX: Int, mouseY: Int): Int{
        val xC = mouseX / (width / columns)
        val yC = mouseY / (height / rows)
        return xC + columns * yC
    }

    fun addOccupant(occupant: Any){
        occupants.add(occupant)
    }

    fun getOccupant(cellIndex: Int): Any?{
        return when {
            occupants.size <= cellIndex -> {
                KApplet.e("Error: index $cellIndex out of bounds, we have ${occupants.size} occupants")
                null
            }
            else -> occupants[cellIndex]
        }
    }

    fun setOccupant(cellIndex: Int, occupant: Any){
        when {
            occupants.size <= cellIndex -> {
                KApplet.e("Error: index $cellIndex out of bounds, we have ${occupants.size} occupants")
                return
            }
            else -> occupants[cellIndex] = occupant
        }
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
}