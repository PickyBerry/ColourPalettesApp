package com.example.recyclerviewhw.repository

import com.example.recyclerviewhw.model.MyCall
import com.example.recyclerviewhw.model.Palette
import com.example.recyclerviewhw.network.RetrofitInstance

class Repository {
    suspend fun getPictures() = RetrofitInstance.listApi.getPalette(MyCall())
}