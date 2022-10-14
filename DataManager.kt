// TODO: fix the imports lol
import android.database.sqlite.SQLiteDatabase

class DataManger() {
    private val db : SQLiteDatabase = context.openOrCreateDatabase("Assessment", Context.MODE_PRIVATE,null)

    init {
        // we can add more tables as needed here
        val modulesCreateQuery = "CREATE TABLE IF NOT EXISTS `Items` (`Name` TEXT NOT NULL, `Price` int NOT NULL, PRIMARY KEY(`Name`) )"
        db.execSQL(modulesCreateQuery)
    }

    // replace argument with whatever Item class we have
    fun addToDB(item: Item) {
        val query = "INSERT INTO Items (name, price) VALUES ('${item.getName()}','${item.getPrice()}')"
        db.execSQL(query)
    }

    // so we can pass an object in or just the name of the item via string
    fun getItemName(name: String) : Item {
        val cursor = db.rawQuery("SELECT * FROM Items WHERE Name = '${name}'", null)
        var name = ""
        var price = 0
        if (cursor.moveToFirst()) {
            do {
                // val id = cursor.getString(cursor.getColumnIndex("Id"))
                name = cursor.getString(cursor.getColumnIndex("Name"))
                price = cursor.getString(cursor.getColumnIndex("Price"))
            } while (cursor.moveToNext())
        }
        val item = Item(name, price)
        cursor.close()
        return item
    }

    // so we can pass an object in or just the name of the item via string
    fun deleteItem(name: String) {
        val query = "DELETE FROM Items where Name = '${name}'"
        db.execSQL(query)
    }

    // replace List< > with whatever Item class we have
    fun listItems() : List<Item> {
        // here too
        val items = mutableListOf<Item>();

        val cursor = db.rawQuery("SELECT * FROM Items", null)

        if (cursor.moveToFirst()) {
            do {
                // val id = cursor.getString(cursor.getColumnIndex("Id"))
                val name = cursor.getString(cursor.getColumnIndex("Name"))
                val price = cursor.getString(cursor.getColumnIndex("Price"))
                val toAdd = Item(name, price.toInt())
                items.add(toAdd)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return items
    }
}