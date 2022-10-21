class Item(val price: Integer, val name: String) {
    private itemName = name
    private itemPrice = price

    init {
        println("Creating class ${name}")
    }

    fun getName() {
        return itemName
    }
    fun getPrice() {
        return itemPrice
    }
}