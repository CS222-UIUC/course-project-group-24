package com.example.cs222receiptscanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addItem.setOnClickListener{
            val db = DBHelper(this, null)

            val name = enterItem.text.toString()
            val age = enterPrice.text.toString()
            db.addName(name, age)

            Toast.makeText(this, name + " added to database", Toast.LENGTH_LONG).show()

            enterItem.text.clear()
            enterPrice.text.clear()
        }

        clearDB.setOnClickListener{
            val db = DBHelper(this, null)
            db.clearDatabase()
            Item.text = "Item\n\n"
            Price.text = "Price\n\n"
        }

        printItem.setOnClickListener{
            val db = DBHelper(this, null)
            val cursor = db.getName()

            Item.text = "Item\n\n"
            Price.text = "Price\n\n"

            cursor!!.moveToFirst()
            Item.append(cursor.getString(cursor.getColumnIndex(DBHelper.ITEM_COL)) + "\n")
            Price.append(cursor.getString(cursor.getColumnIndex(DBHelper.PRICE_COL)) + "\n")

            while(cursor.moveToNext()){
                Item.append(cursor.getString(cursor.getColumnIndex(DBHelper.ITEM_COL)) + "\n")
                Price.append(cursor.getString(cursor.getColumnIndex(DBHelper.PRICE_COL)) + "\n")
            }
            cursor.close()
        }
    }
}