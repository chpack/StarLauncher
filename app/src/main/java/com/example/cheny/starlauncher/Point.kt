package com.example.cheny.starlauncher

import android.view.MotionEvent
import android.view.View

data class Point(var x: Double, var y: Double) {

    constructor(event: MotionEvent) : this(event.x, event.y)
    constructor(xf: Float, yf: Float) : this(xf.toDouble(), yf.toDouble())
    constructor(w: Double) : this(w, w)


    operator fun get(i: Int): Point {
        if (i < PanelAttrs.num)
            return this + Point(Math.sin(Math.toRadians(i * PanelAttrs.angle)), Math.cos(Math.toRadians(i * PanelAttrs.angle))) * PanelAttrs.armLength
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

fun View.setPosition(pos: Point) {
    x = pos.x.toFloat()
    y = pos.y.toFloat()
}


