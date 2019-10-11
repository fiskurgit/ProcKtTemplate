import gridutils.BasicCell

class Processing: KApplet() {

    override fun setup() {
        fill(BLACK)
        grid.prepopulate(BasicCell::class)
    }

    override fun draw() {
        background(WHITE)

        grid.draw(this)

        grid.occupants<BasicCell>().forEachIndexed { index, cell ->
            if(cell.active){
                val cellOrigin = grid.cellOrigin(index)
                circle(cellOrigin.x, cellOrigin.y, grid.cellDiam())
            }
        }
    }

    override fun mouseClicked() {
        val occupant = grid.occupantAt<BasicCell>(mouseX, mouseY)
        occupant.active = !occupant.active
    }
}