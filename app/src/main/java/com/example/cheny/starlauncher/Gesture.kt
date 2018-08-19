package com.example.cheny.starlauncher

class Gesture(private var ges: MutableList<Int>) {

    constructor(gesStr: String): this(MutableList(0){0}) {
        for (i in gesStr) {
            ges.add(i.toInt())
        }
    }

    operator fun plus(last: Int) : MutableList<Int> {
        return (ges + last).toMutableList()
    }

    operator fun minus(ele: Int):MutableList<Int> {
        return (ges - ele).toMutableList()
    }

    override fun toString(): String {
        return ges.toString().filter { it.isDigit() }
//        return super.toString()
    }
}