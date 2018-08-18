package com.example.cheny.starlauncher

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout


object PanelAttrs {
    var size = Point(250.0)
    var num = 6
    var armLength = 300.0
    var angle = 60.0
}

@SuppressLint("ViewConstructor")
class Panel(context: Context, var operatorArea: ViewGroup) : ImageView(context) {
    var position = Point(0.0, 0.0)

    private var subPanel = MutableList<Panel?>(6) { null }

    init {
        super.setImageResource(R.drawable.empty)
        layoutParams = RelativeLayout.LayoutParams(PanelAttrs.size.x.toInt(), PanelAttrs.size.y.toInt())
    }

    constructor(context: Context, operatorArea: ViewGroup, drawable: Drawable) : this(context, operatorArea) {
        super.setImageDrawable(drawable)
    }

    fun unfold(pos: Point) {
        alpha = 1F
        for (i in 0 until PanelAttrs.num) {
            get(i).alpha = 1F
            get(i).setPos(pos[i])
        }
    }

    /**
     * fold back the panels
     */
    fun fold(next: Int) {
        for (i in 0 until PanelAttrs.num) {
            if (i != next)
                get(i).alpha = 0F
        }
        this.alpha = 0F
    }

    fun fold() {
        fold(-1)
    }

    /**
     * Turn to next Panel.
     */
    fun turn(pos: Point): Panel {
        val next = position.get(pos)

        if (next == 6) {
            return this
        } else {
            fold(next)
            get(next).unfold(position.get(next))
            return get(next)
        }
    }

    /**
     * Get the Panel on that position.
     */
    operator fun get(i: Int): Panel {
        if (subPanel[i] == null) {
            subPanel[i] = Panel(context, operatorArea)
            operatorArea.addView(subPanel[i])
        }
        return subPanel[i]!!
    }

    /**
     * Set a new position.
     */
    fun setPos(pos: Point) {
        position = pos
        x = (pos.x.toFloat() - PanelAttrs.size.x / 2.0).toFloat()
        y = (pos.y.toFloat() - PanelAttrs.size.y / 2.0).toFloat()
    }


}