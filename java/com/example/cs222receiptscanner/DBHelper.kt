package com.example.cs222receiptscanner

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                ITEM_COL + " TEXT," +
                PRICE_COL + " TEXT" + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun clearDatabase() {
        val db = this.writableDatabase
        val query = ("DROP TABLE " + TABLE_NAME)
        val createq = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                ITEM_COL + " TEXT," +
                PRICE_COL + " TEXT" + ")")
        db.execSQL(query)
        db.execSQL(createq)
    }

    fun addName(name : String, age : String ){
        val values = ContentValues()
        values.put(ITEM_COL, name)
        values.put(PRICE_COL, age)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getName(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }

    companion object{
        private val DATABASE_NAME = "CS222_Receipt Scanner"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "Items"
        val ID_COL = "id"
        val ITEM_COL = "name"
        val PRICE_COL = "price"
    }
}