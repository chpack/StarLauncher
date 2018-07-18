package com.example.cheny.starlauncher

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer
import android.support.constraint.ConstraintLayout
import android.view.ViewGroup
import android.widget.ImageView

var iconSize = 200
var radius = 300
var panelSize = 800

@SuppressLint("StaticFieldLeak")
var cont : Context? = null

@SuppressLint("StaticFieldLeak")
var panelContainer : ConstraintLayout? = null

var empty: Drawable? = null


//private var postions = Array(7, {i ->
//    if (i == 0)
//        Pot() - iconSize / 2 + panelSize / 2
//    else
//        Pot(Math.cos(Math.toRadians(i * 60.0 + 150)), Math.sin(Math.toRadians(i * 60.0 + 150))) * radius - iconSize/2 + panelSize/2
//})

/**
 * Init the panels
 */
fun initPanel(iconS: Int, rad: Int, panelS: Int, context: Context, contain: ConstraintLayout, emp: Drawable) {
//        empty = resources.getDrawable(R.drawable.empty)
//        panelContainer = panel_container
//        panelContainer!!.layoutParams = ConstraintLayout.LayoutParams(panelSize, panelSize)
//        cont = this
    iconSize = iconS
    panelSize = panelS
    radius = rad
    cont = context
    panelContainer = contain
    panelContainer!!.layoutParams = ConstraintLayout.LayoutParams(panelSize, panelSize)
    empty = emp
}


class Panel(var index: Int, imageRes: String = ""){

    var base = ImageView(cont)
    private var children = Array<Panel?>(7,{null})
    private var full = false

    lateinit var openAni: AnimatorSet
    lateinit var closeAni: AnimatorSet

    /**
     *  This constructor is for the panel that just show an icon, and most time it won't be touched.
     *  So just init the icon, position and index.
     */
    init {
        children[index] = this

        base.x = postions[index].x
        base.y = postions[index].y
        base.layoutParams = ViewGroup.LayoutParams(iconSize, iconSize)

        if (imageRes == "")
            base.setImageResource(R.drawable.empty)
        else
            base.setImageDrawable(loadImage(imageRes))

        panelContainer?.addView(base)
    }

    /**
     *  This constructor is for the others panel, Those will be touched and open.
     */
    constructor(order: List<Int>, imageRes: String) : this(order[0]) {
        full = true
        index = order[0]
        children = Array(7, {i ->
            if (i == index)
                this
            else
                Panel(i)
        })
        add(order, imageRes)
    }


    /**
     *  Add the children
     */
    fun add(commandList: List<Int>, imageRes: String) {
        createAnimator()
        if (commandList.size == 1) {
            base.setImageDrawable(loadImage(imageRes))
        }else {
            if (children[commandList[0]] == null)
                children[commandList[0]] = Panel(commandList.drop(1), imageRes)
            else
                children[commandList[0]]!!.add(commandList.drop(1), imageRes)
        }
    }

    /**
     *  Create the animations when it open and close
     */
    fun createAnimator() {
        openAni = AnimatorSet()
        closeAni = AnimatorSet()
        for (i: Int in 0..6) {
            openAni.playTogether(ObjectAnimator.ofFloat(children[i]!!.base, "alpha", 0f, 1f))
            openAni.playTogether(ObjectAnimator.ofFloat(children[i]!!.base, "X", postions[index].x, postions[i].x))
            openAni.playTogether(ObjectAnimator.ofFloat(children[i]!!.base, "Y", postions[index].y, postions[i].y))

            closeAni.playTogether(ObjectAnimator.ofFloat(children[i]!!.base, "alpha", 1f, 0f))
//            closeAni.playTogether(ObjectAnimator.ofFloat(children[i]!!.base, "X", children[i]!!.base.x, postions[index].x))
//            closeAni.playTogether(ObjectAnimator.ofFloat(children[i]!!.base, "Y", children[i]!!.base.y, postions[index].y))
        }
        openAni.duration = 200
        closeAni.duration = 200
    }
    /**
     * Play The animator when the panel is opening
     */
    fun open() {
        openAni.start()
    }

    /**
     * Play The cloaseAni when the panel is closing
     */
    fun close() {
        closeAni.start()
    }

    public fun getChild(index: List<Int>): Panel {
        return when {
            index.size == 0 -> this
            index.size == 1 -> this[index[0]]
            else -> children[index[0]]!!.getChild(index.drop(0))
        }
    }

    operator fun get(index: Int): Panel {
        if (!children[index]!!.full)
            children[index] = Panel(listOf(index), "empty")
        return children[index] as Panel

    }


    private fun loadImage(imageRes: String): Drawable? {
        return when(imageRes) {
            "empty" -> empty
            else -> empty
        }
    }


}
