import processing.core.PVector
import java.awt.Color

class Processing: KApplet() {

    var clickedCell: PVector? = null

    override fun setup() {
        noFill()
        stroke(WHITE)
    }

    override fun draw() {
        background(WHITE)

        drawGrid()

        if(clickedCell != null){
            fill(Color.PINK.rgb)
            ellipse(clickedCell!!.x, clickedCell!!.y, grid.cellWidth(), grid.cellHeight())
        }
    }

    override fun mouseClicked() {
        val clickedIndex = cellIndex()
        clickedCell = cellOrigin(clickedIndex)
    }
}