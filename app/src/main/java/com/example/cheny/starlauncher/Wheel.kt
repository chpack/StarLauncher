package com.example.cheny.starlauncher

class Wheel(private val pos: Int,private val self: Item) {

    private var children = Array<Wheel?>(7, {null})

    fun onShow(start: Int) {
        self.onshow(start, pos)
    }

    fun onShow() {
        for (i in 0..7) {
            children[i]?.onShow(pos)
        }
    }

    fun active() {

    }

    fun next(index: Int) {
        children[index].onShow(pos)
    }

    fun close() {
        for (i in 0..7) {
            children[i]?.close()
        }
    }

}