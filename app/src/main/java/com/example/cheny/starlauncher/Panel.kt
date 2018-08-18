package com.example.cheny.starlauncher

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView


object PanelAttrs {
    var size = Point(100.0)
    var num = 6
    var armLength = 400.0
    var angle = 60.0
}

@SuppressLint("ViewConstructor")
class Panel(context: Context, var operatorArea: ViewGroup) : ImageView(context) {
    private var subPanel = MutableList<Panel?>(6) { null }

    init {
        super.setImageResource(R.drawable.empty)
    }

    constructor(context: Context, operatorArea: ViewGroup, drawable: Drawable) : this(context, operatorArea) {
        super.setImageDrawable(drawable)
    }

    fun expane(pos: Point) {
        for (i in 0 until PanelAttrs.num) {
            get(i).setPosition(pos[i])
        }
    }

    operator fun get(i: Int): Panel {
        if (subPanel[i] == null) {
            subPanel[i] = Panel(context, operatorArea)
            operatorArea.addView(subPanel[i])
        }
        return subPanel[i]!!
    }

}