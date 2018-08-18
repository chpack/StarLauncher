package com.example.cheny.starlauncher

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView


class Panel(context: Context) : ImageView(context) {
//    private var subPanel = Panel[6]
    private var subPanel = List<Panel?>(6) {null}

    init {
        super.setImageResource(R.drawable.empty)
    }

    constructor(context: Context, drawable: Drawable) : this(context) {
        super.setImageDrawable(drawable)
    }
}