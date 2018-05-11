package com.example.cheny.starlauncher

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
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


private var postions = Array(7, {i ->
    if (i == 0)
        Pot() - iconSize / 2 + panelSize / 2
    else
        Pot(Math.sin(Math.toRadians(i * 60.0)), Math.cos(Math.toRadians(i * 60.0))) * radius - iconSize/2 + panelSize/2
})


class Panel(var index: Int, image: Drawable? = null){

    var base = ImageView(cont)
    private var children = Array<Panel?>(7,{null})

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

        if (image == null)
            base.setImageResource(R.drawable.empty)
        else
            base.setImageDrawable(image)

        panelContainer?.addView(base)
    }

    /**
     *  This constructor is for the others panel, Those will be touched and open.
     */
    constructor(order: List<Int>, image: Drawable) : this(order[0]) {
        index = order[0]
        children = Array(7, {i ->
            if (i == index)
                this
            else
                Panel(i)
        })
        add(order, image)
    }


    /**
     *  Add the children
     */
    fun add(commandList: List<Int>, image: Drawable) {
        createAnimator()
        if (commandList.size == 1) {
            base.setImageDrawable(image)
        }else {
            if (children[commandList[0]] == null)
                children[commandList[0]] = Panel(commandList.drop(1), image)
            else
                children[commandList[0]]!!.add(commandList.drop(1), image)
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

}
