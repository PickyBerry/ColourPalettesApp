package com.example.recyclerviewhw.model

import com.google.gson.annotations.SerializedName

class Palette (
    @SerializedName(value = "result") val colors: Array<Array<Int>>?
)