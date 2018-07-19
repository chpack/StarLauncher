package com.example.cheny.starlauncher

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View

@Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }

}
