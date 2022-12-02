package com.example.recyclerviewhw.repository

import com.example.recyclerviewhw.model.API
import com.example.recyclerviewhw.model.MyCall
import com.example.recyclerviewhw.model.Palette
import com.example.recyclerviewhw.model.PaletteItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject


class Repository(private val api: API) {

    suspend fun getPalettes() = api.getPalette(MyCall())

    private val favorites=mutableListOf<PaletteItem>()

    fun addFavorite(paletteItem: PaletteItem){
        favorites.add(paletteItem)
    }


    fun getFavorites()=favorites

    fun removeFavorite(paletteItem: PaletteItem){
        favorites.remove(paletteItem)
    }

}