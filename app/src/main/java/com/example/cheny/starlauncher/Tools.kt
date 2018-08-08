package com.example.cheny.starlauncher

import android.content.Context
import android.util.Log
import android.widget.Toast

object Tools {

    /**
     * functions for debug.
     * i is a counter
     */
    var i = 0;
    fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }
    fun d() {
        d("succ", "succ$i")
        i +=1
    }
    fun d(msg: String) {
        d("undef", msg)
    }


    fun t(context: Context,msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
    fun t(context: Context) {
        t(context, "succ$i")
        i += 1
    }


}