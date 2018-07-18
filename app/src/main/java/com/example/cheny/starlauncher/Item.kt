package com.example.cheny.starlauncher

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.ViewGroup
import android.widget.ImageView


var postions = Array(7, {i ->
    if (i == 0)
        Pot() - iconSize / 2 + panelSize / 2
    else
        Pot(Math.cos(Math.toRadians(i * 60.0 + 150)), Math.sin(Math.toRadians(i * 60.0 + 150))) * radius - iconSize/2 + panelSize/2
})

class Item(context: Context, resources: Resources, rootView: ViewGroup,
           private val pkg: String, private val name: String, iconBM: ByteArray) {

    private var view:ImageView = ImageView(context)
    private lateinit var animator: AnimatorSet

    init {
        view.setImageDrawable(BitmapDrawable(resources, BitmapFactory.decodeByteArray(iconBM,0,iconBM.size)))
        view.alpha = 0f
        rootView.addView(view)
    }

    fun onshow(start: Int, end: Int) {
        if (!animator.isPaused)
            animator.pause()
        animator = AnimatorSet()

        animator.playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "X", postions[start].x, postions[end].x),
                ObjectAnimator.ofFloat(view, "Y", postions[start].y, postions[end].y) )

        animator.duration = 200;
        animator.start()
    }

    fun close() {
        if (animator.isPaused){
            animator = AnimatorSet()
        }else {
            animator.pause()
        }
        animator.playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f))

        animator.duration = 200;
        animator.start()
    }


//    for (i: Int in 0..6) {
//        openAni.playTogether(ObjectAnimator.ofFloat(children[i]!!.base, "alpha", 0f, 1f))
//        openAni.playTogether(ObjectAnimator.ofFloat(children[i]!!.base, "X", postions[index].x, postions[i].x))
//        openAni.playTogether(ObjectAnimator.ofFloat(children[i]!!.base, "Y", postions[index].y, postions[i].y))
//
//        closeAni.playTogether(ObjectAnimator.ofFloat(children[i]!!.base, "alpha", 1f, 0f))
////            closeAni.playTogether(ObjectAnimator.ofFloat(children[i]!!.base, "X", children[i]!!.base.x, postions[index].x))
////            closeAni.playTogether(ObjectAnimator.ofFloat(children[i]!!.base, "Y", children[i]!!.base.y, postions[index].y))
//    }
//    openAni.duration = 200
//    closeAni.duration = 200


}