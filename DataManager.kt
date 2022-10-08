// TODO: fix the imports lol
import android.database.sqlite.SQLiteDatabase

class DataManger() {
    private val db : SQLiteDatabase = context.openOrCreateDatabase("Assessment", Context.MODE_PRIVATE,null)

    init {
        // we can add more tables as needed here
        val modulesCreateQuery = "CREATE TABLE IF NOT EXISTS `Items` ( `Id` TEXT NOT NULL, `Name` TEXT NOT NULL, `Price` int NOT NULL, PRIMARY KEY(`id`) )"
        db.execSQL(modulesCreateQuery)
    }

    // replace argument with whatever Item class we have
    fun addToDB(item: Item) {
        val query = "INSERT INTO Items (name, price) VALUES ('${item.name}','${item.price}')"
        db.execSQL(query)
    }

    // so we can pass an object in or just the name of the item via string
    fun getItemName(name: String) : List<Item> {
        val items = mutableListOf<Item>()
        val cursor = db.rawQuery("SELECT * FROM Items WHERE Name = '${name}'", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex("Id"))
                val name = cursor.getString(cursor.getColumnIndex("Name"))
                val price = cursor.getString(cursor.getColumnIndex("Price"))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return items
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
                val id = cursor.getString(cursor.getColumnIndex("Id"))
                val name = cursor.getString(cursor.getColumnIndex("Name"))
                val price = cursor.getString(cursor.getColumnIndex("Price"))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return items
    }
}