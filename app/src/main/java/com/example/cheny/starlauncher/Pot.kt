package com.example.cheny.starlauncher

import android.util.EventLog
import android.view.MotionEvent

/**
 * Created by cheny on 17.12.13.
 * To response the points, and calculate which user choice
 */
class Pot(var x: Float = 0f, var y: Float = 0f) {

    constructor(xd: Double, yd: Double) : this(xd.toFloat(), yd.toFloat()) {}
    constructor(xi: Int, yi: Int) : this(xi.toFloat(), yi.toFloat()) {}
    constructor(event: MotionEvent) : this() {
        fromEvent(event)
    }

    // where did user touched
    fun inWhichArea(another: Pot): Int {
        return when (dis(another)?.toInt()) {
            in 0..200 -> 0
            in 200..400 -> angle(another) / 60 + 1
            else -> 7
        }
    }

    // Distent of the start to now
    private fun dis(pot2: Pot): Double? {
        return Math.pow(Math.pow(x - pot2.x.toDouble(), 2.0) + Math.pow(y - pot2.y.toDouble(), 2.0), 0.5)
    }

    // angle, left is 0, up is 90
    private fun angle(pot2: Pot): Int {
        val dx = pot2.x - this.x
        val dy = pot2.y - this.y
        return (Math.toDegrees(Math.atan2(dy.toDouble(), dx.toDouble())) + 180).toInt()
    }

    fun fromEvent(event: MotionEvent) {
        x = event.x
        y = event.y
    }


    operator fun times(t: Int) = Pot(x * t, y * t)

    operator fun minus(t: Pot) = Pot(x - t.x, y - t.y)
    operator fun minus(t: Int) = Pot(x - t, y - t)

    operator fun plus(t: Pot) = Pot(x + t.x, y + t.y)
    operator fun plus(t: Int) = Pot(x + t, y + t)
}