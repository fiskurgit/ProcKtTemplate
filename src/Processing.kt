import gridutils.BasicCell

class Processing: KApplet() {

    override fun setup() {
        fill(BLACK)
        stroke(WHITE)
        prepopulate(gridutils.BasicCell::class)
    }

    override fun draw() {
        background(WHITE)

        drawGrid()

        grid.occupants.forEachIndexed { index, cell ->
            if((cell as BasicCell).active){
                val activeOrigin = grid.cellOrigin(index)
                ellipse(activeOrigin.x, activeOrigin.y, grid.cellWidth(), grid.cellHeight())
            }
        }
    }

    override fun mouseClicked() {
        val clickedIndex = mouseLocationCellIndex()
        val occupant = grid.getOccupant<BasicCell>(clickedIndex)
        if(occupant != null) occupant.active = !occupant.active

    }
}