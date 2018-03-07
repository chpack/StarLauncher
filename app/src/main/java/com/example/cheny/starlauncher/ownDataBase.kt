package com.example.cheny.starlauncher

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by cheny on 18.1.12.
 * Deal with the database
 */
class ownDataBase constructor(var db: SQLiteDatabase){
    init {
//        db.execSQL("DROP TABLE IF EXISTS person")
        db.execSQL("CREATE TABLE IF NOT EXISTS gesture_lists (_id INTEGER PRIMARY KEY AUTOINCREMENT, gesture TEXT, type TEXT, action TEXT, package TEXT, name TEXT, status TEXT")
        val a = db.rawQuery("SELECT name WHERE type='set';", null)
        if (a.count < 1) {
//            db.execSQL("INSERT INTO person (gesture, package, name, status, type) VALUES ('[1, 6, 1]','start','set','enable','set_map');");
            val values = ContentValues()
            values.put("gesture", "[1, 6, 1]")
            values.put("type", "launcher")
            values.put("action", "list")
            db.insert("gesture_list", null, values)
        }
    }

    // Add a new gesture
//    @SuppressLint("Recycle")
//    fun additem(packagename: String, name: String, gesture: String) {
//        val result = db.rawQuery("SELECT gesture FROM person WHERE name='$name';",null)
//        if (result.count == 0) {
//            db.execSQL("INSERT INTO person (packagename, name, gesture, status) VALUES ('$packagename', '$name', '$gesture', enable)")
//        } else {
//            db.execSQL("UPDATE person SET gesture = '$gesture' WHERE name = '$name'")
//        }
//    }

    // Find the app which gesture is provide
    fun pack(gesture: String): List<String> {
        val result = db.rawQuery("SELECT package, name, type FROM person WHERE gesture='$gesture';",null)
        return if (result.count != 0) {
            result.moveToFirst()
            val tmp = listOf<String>(result.getString(0), result.getString(1), result.getString(2))
            result.close()
            Log.d("asdf", tmp.toString())
            tmp
        } else {
            result.close()
            listOf("","","none")
        }
    }

    // Delete a gesture
    private fun delete(key: String, value: String) {
        db.execSQL("DELETE WHERE $key = '$value'")
    }

    fun set(gesture: String, packagename: String, name: String) {
        db.execSQL("INSERT INTO person (packagename, name, gesture, status, type) VALUES ('$packagename', '$name', '$gesture', 'enable', 'app')")
    }

    fun add(gesture: String, packagename: String, name: String) {
        delete("gesture", gesture)
        set(gesture,packagename, name)
    }

//    @SuppressLint("Recycle")
//    fun Lotout() {
//        val result = db.rawQuery("SELECT gesture, package, name, type FROM person",null)
//        Log.d("appp", "afsdfasd")
//
//        result.moveToFirst()
//        while (!result.isAfterLast) {
//            Log.d("appp", "${result.getString(0)}${result.getString(1)}${result.getString(2)}")
//            result.moveToNext()
//        }
//    }
}

class data (context: Context): SQLiteOpenHelper(context, "map.db",null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE IF NOT EXISTS person (_id INTEGER PRIMARY KEY AUTOINCREMENT, gesture TEXT, package TEXT, name TEXT, status TEXT, type TEXT);")
        db.execSQL("INSERT INTO person (gesture, package, name, status, type) VALUES ('[1, 6, 1]','start','set','enable','set_map');")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}