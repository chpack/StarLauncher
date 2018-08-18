package com.example.cheny.starlauncher

import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout

data class Point(var x: Double, var y: Double) {

    constructor(event: MotionEvent) : this(event.x, event.y)
    constructor(xf: Float, yf: Float) : this(xf.toDouble(), yf.toDouble())
    constructor(w: Double) : this(w, w)

    fun get(pos: Point): Int {
        if (dis(pos) <= PanelAttrs.armLength / 2)
            return 6
        val dpos = pos - this
        var degree = Math.toDegrees(Math.atan2(dpos.x, dpos.y)).toInt()
        // Magic about atan2 and sin, cos. DON'T TOUCH IT before you understand it
        degree =  (degree + 390)%360
        return degree / 60
    }

    fun dis(pos: Point): Int {
        return Math.pow(Math.pow(x - pos.x, 2.0) + Math.pow(y - pos.y, 2.0), 0.5).toInt()
    }

    operator fun get(i: Int): Point {
        if (i < PanelAttrs.num) {
//            return this + Point(Math.sin(Math.toRadians(i * PanelAttrs.angle)), Math.cos(Math.toRadians(i * PanelAttrs.angle))) * PanelAttrs.armLength
            val relativePos = Point(Math.sin(Math.toRadians(i * PanelAttrs.angle)), Math.cos(Math.toRadians(i * PanelAttrs.angle)))
            return this + relativePos * PanelAttrs.armLength
        }
        return Point(0.0, 0.0)
    }

    operator fun plus(b: Point): Point {
        return Point(x + b.x, y + b.y)
    }

    operator fun minus(b: Point): Point {
        return Point(x - b.x, y - b.y)
    }

    operator fun times(t: Double): Point {
        return Point(x * t, y * t)
    }

    operator fun div(t: Double): Point {
        return Point(x / t, y / t)
    }
}
