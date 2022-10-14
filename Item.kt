class Item(n: String, p: Int) {
    private val name = n
    private val price = p

    init {
        println("Creating class $name")
    }

    fun getName(): String {
        return this.name
    }
    fun getPrice(): Int {
        return this.price

}