package com.example.recyclerviewhw.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recyclerviewhw.model.Palette
import com.example.recyclerviewhw.model.PaletteItem
import com.example.recyclerviewhw.repository.Repository


class FavoritesViewModel(
    app: Application,
    private val repository: Repository
) : AndroidViewModel(app) {


    private var _favorites = MutableLiveData<List<PaletteItem>>()
    val favorites: LiveData<List<PaletteItem>> = _favorites

    init {
        getFavorites()
    }

    fun getFavorites() {
        _favorites.postValue(repository.getFavorites())
    }


    fun removeFavorite(paletteItem: PaletteItem) = repository.removeFavorite(paletteItem)


}