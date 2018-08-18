package com.example.cheny.starlauncher

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
class MainActivity : Activity() {
    private lateinit var root: Panel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        root = Panel(this, operation_area)
        operation_area.addView(root)
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }

    /**
     * Start app list activity
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (event?.action == MotionEvent.ACTION_UP){
////            startActivity(Intent(this, AppList::class.java))
//        }
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                root.setPosition(Point(event))
                root.expane(Point(event))
//                root.x = event.x
//                root.y = event.y
            }
        }

        return true
    }

    /**
     * Do nothing when back button pressed
     */
    override fun onBackPressed() {
//        super.onBackPressed()
    }

}
