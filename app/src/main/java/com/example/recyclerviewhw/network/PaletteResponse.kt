package com.example.recyclerviewhw.network

import com.example.recyclerviewhw.data.Palette
import com.google.gson.annotations.SerializedName

//Response from api comes as an array of 5 arrays of three rgb colors
//We can turn this into a list of 5 colors assigning one integer for each rgb set
class PaletteResponse(
    @SerializedName(value = "result") val colors: Array<Array<Int>>?
) {
    fun toPalette() = Palette(colors?.map { getIntFromColor(it[0], it[1], it[2]) }?.toList())

    private fun getIntFromColor(red: Int, green: Int, blue: Int): Int {
        val red = red shl 16 and 0x00FF0000 //Shift red 16-bits and mask out other stuff
        val green = green shl 8 and 0x0000FF00 //Shift Green 8-bits and mask out other stuff
        val blue = blue and 0x000000FF //Mask out anything not blue.
        return -0x1000000 or red or green or blue //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
}