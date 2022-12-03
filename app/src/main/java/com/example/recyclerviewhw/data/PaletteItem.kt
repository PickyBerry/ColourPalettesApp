package com.example.recyclerviewhw.data

//data class for palette item as represented in recyclerviews
data class PaletteItem(val palette: Palette, var favorite: Boolean) {

    //overriding toString() function for simpler saving and loading in internal storage
    override fun toString(): String {
        var s = ""
        for (i in 0..3) {
            s += palette[i]
            s += ","
        }
        s += palette[4]
        return s
    }
}