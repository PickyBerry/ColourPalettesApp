package com.example.recyclerviewhw.repository

import com.example.recyclerviewhw.model.MyCall
import com.example.recyclerviewhw.model.Palette
import com.example.recyclerviewhw.model.PaletteItem
import com.example.recyclerviewhw.network.RetrofitInstance

object Repository {

    suspend fun getPalettes() = RetrofitInstance.listApi.getPalette(MyCall())

    private val favorites=mutableListOf<PaletteItem>()

    fun addFavorite(paletteItem: PaletteItem){
        favorites.add(paletteItem)
    }

    fun getFavorites()=favorites

    fun removeFavorite(paletteItem: PaletteItem){
        favorites.remove(paletteItem)
    }

}