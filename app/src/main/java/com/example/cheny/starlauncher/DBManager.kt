package com.example.cheny.starlauncher

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcel
import android.os.Parcelable
import com.example.cheny.starlauncher.R.drawable.list
import java.io.ByteArrayOutputStream

/**
 * Created by cheny on 18.3.6.
 * Create this for manager the database.
 */

class DBManager(context: Context, private val res: Resources,private val tabname: String) : SQLiteOpenHelper(context, tabname, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS map (_id INTEGER PRIMARY KEY AUTOINCREMENT, gesture TEXT, type TEXT, pkg TEXT, name TEXT, icon BLOB)")
        val args = ContentValues()
        args.put("gesture", listOf(6,1,6).toString())
        args.put("type", "launcher")
        args.put("pkg", "list")
        args.put("name", "")
        args.put("icon", list)
        db?.insert(tabname,null, args)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
//    var db: SQLiteDatabase = OraginDB(context,"map.db", null, 1).writableDatabase

    fun add(gesture: String, type: String, pkg: String, name: String, icon: ByteArray) {
        val args = ContentValues()
        args.put("gesture", gesture)
        args.put("type", type)
        args.put("pkg", pkg)
        args.put("name", name)
        args.put("icon", icon)
        writableDatabase.insert(tabname,null, args)
    }


    /**
     * Find the action which the gesture map to.
     */
    @SuppressLint("Recycle")
    fun search(gesture: String): List<String> {
        val columns = arrayOf("type", "pkg", "name", "icon")
        val result = readableDatabase.query("map", columns, "gesture = ?", arrayOf(gesture), null, null, null)
        val final = if (result.count != 0) {
            result.moveToFirst()
            arrayOf(result.getString(0),// type
                    result.getString(1),/// package
                    result.getString(2))// name
        } else {
            arrayOf("","","")
        }
        return final.toList()
    }


    /**
     *  Delete the item by package name
     */
    fun delete(pkg: String){
        writableDatabase.delete("map", "pkg = ?", arrayOf(pkg))
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
            item.put("pkg", pack)
            item.put("name", name)
            writableDatabase.insert("map", null, item)
            true
        }else false
    }


    /**
     * For some special usage.
     */
    @SuppressLint("Recycle")
    fun scanGesture(name: String): String {
        val result = readableDatabase.query("map", arrayOf("gesture"), "name = ?", arrayOf(name), null, null, null)
        return if (result.count != 0) {
            result.moveToFirst()
            result.getString(0)
        } else ""
    }


    /**
     * Return all items to init panels.
     */
    fun all():Cursor {
        return readableDatabase.query("map", arrayOf("gesture", "pkg"), "1=1", null, null, null, null)
    }

}
