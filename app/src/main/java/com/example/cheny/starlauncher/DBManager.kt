package com.example.cheny.starlauncher

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

/**
 * Created by cheny on 18.3.6.
 * Create this for manager the database.
 */

class DBManager(context: Context) {
    var db: SQLiteDatabase = OraginDB(context,"map.db", null, 1).writableDatabase


    /**
     * Find the action which the gesture map to.
     *
     */
    @SuppressLint("Recycle")
    fun search(gesture: String): List<String> {
        val columns = arrayOf("type", "action", "package", "name", "arguments")
        val result = db.query("map", columns, "gesture = ?", arrayOf(gesture), null, null, null)
        val final = if (result.count != 0) {
            result.moveToFirst()
            arrayOf(result.getString(0),// type
                    result.getString(1),// action
                    result.getString(2),/// package
                    result.getString(3),// name
                    result.getString(4))// arguments
        } else {
            arrayOf("","","","","")
        }
        return final.toList()
    }


    /**
     *  Delete the item by package name
     */
    fun delete(pack: String){
        db.delete("map", "package = ?", arrayOf(pack))
    }


    /**
     *  Updata the item
     *
     */
     fun updata(gesture: String, pack: String, name: String, arguments: String): Boolean {
        val item = ContentValues()
        return if (search(gesture)[0] == "") {
            delete(pack)
            item.put("gesture", gesture)
            item.put("type", "app")
            item.put("action", "start")
            item.put("package", pack)
            item.put("name", name)
            item.put("arguments", arguments)
            db.insert("map", null, item)
            true
        }else false
    }


    /**
     * For some special usage.
     */
    @SuppressLint("Recycle")
    fun scanGesture(name: String): String {
        val result = db.query("map", arrayOf("gesture"), "name = ?", arrayOf(name), null, null, null)
        return if (result.count != 0) {
            result.moveToFirst()
            result.getString(0)
        } else ""
    }
}
