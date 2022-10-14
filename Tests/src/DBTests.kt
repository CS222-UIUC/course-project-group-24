import junit.framework.TestCase.assertEquals
import org.junit.Test

class DBTests {
    @Test
    fun testAddToDB() {
        val item = Item("hotdogs", 35)
        val result = mutableListOf(Item("hotdogs", 35))
        val db = DataManger()
        db.addToDB(item)
        val items = db.listItems()
        assertEquals(result, items)
    }
    @Test
    fun testRemoveFromDB() {
        val item = Item("hotdogs", 35)
        val result = mutableListOf<Item>()
        val db = DataManger()
        db.addToDB(item)
        db.deleteItem("hotdogs")
        val items = db.listItems()
        assertEquals(result, items)
    }
    @Test
    fun
}