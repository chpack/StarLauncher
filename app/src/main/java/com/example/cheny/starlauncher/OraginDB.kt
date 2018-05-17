package com.example.cheny.starlauncher

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by cheny on 18.3.6.
 * Cragte this just for deal with the database.
 */


@Suppress("UNREACHABLE_CODE")
class OraginDB(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int): SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Create the table and add two default gesture.
        db?.execSQL("CREATE TABLE IF NOT EXISTS map (_id INTEGER PRIMARY KEY AUTOINCREMENT, gesture TEXT, type TEXT, pkg TEXT, name TEXT)")
        val appList = ContentValues()
        appList.put("gesture", "[1, 6, 1]")
        appList.put("type", "launcher")
        appList.put("package", "list")
        appList.put("name", "")
        db?.insert("map", null, appList)
        val settingFrag = ContentValues()
        settingFrag.put("gesture", "[1, 6, 5]")
        settingFrag.put("type", "launcher")
        settingFrag.put("package", "setting")
        settingFrag.put("name", "")
        db?.insert("map", null, settingFrag)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}