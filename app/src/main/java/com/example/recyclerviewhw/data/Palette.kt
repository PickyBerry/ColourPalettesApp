package com.example.recyclerviewhw.data

//Default data class for palette - basically a list of 5 integer colors
data class Palette(val colors: List<Int>?){

    //overriding palette[i] operator
    operator fun get(i: Int): Int{
        return colors!![i]
    }
}
