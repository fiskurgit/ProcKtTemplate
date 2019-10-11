import gridutils.BasicCell
import java.awt.Color

class Processing: KApplet() {

    override fun setup() {
        noFill()
        stroke(WHITE)
        prepopulate(gridutils.BasicCell::class)
    }

    override fun draw() {
        background(WHITE)

        drawGrid()

        var index = 0
        grid.occupants.forEach { cell ->
            if((cell as BasicCell).active){
                val activeOrigin = grid.cellOrigin(index)
                fill(Color.PINK.rgb)
                ellipse(activeOrigin.x, activeOrigin.y, grid.cellWidth(), grid.cellHeight())
            }

            index++
        }
    }

    override fun mouseClicked() {
        val clickedIndex = cellIndex()
        val occupant = grid.getOccupant(clickedIndex) as BasicCell
        occupant.active = !occupant.active
    }
}