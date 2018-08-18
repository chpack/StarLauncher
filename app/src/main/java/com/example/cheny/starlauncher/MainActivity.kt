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
    private lateinit var current: Panel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        root = Panel(this, operation_area)
        operation_area.addView(root)
        current = root

        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }

    /**
     * Start app list activity
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                root.setPos(Point(event))
                root.unfold(Point(event))
            }
            MotionEvent.ACTION_MOVE -> {
                current = current.turn(Point(event))
            }
            MotionEvent.ACTION_UP -> {
                current.fold()
                current = root
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
