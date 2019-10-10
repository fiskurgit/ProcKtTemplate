class P5Kt {
    companion object {

        lateinit var sketch: Processing

        @JvmStatic
        fun main(args: Array<String>) {
            sketch = Processing()
            sketch.run()
        }
    }
}