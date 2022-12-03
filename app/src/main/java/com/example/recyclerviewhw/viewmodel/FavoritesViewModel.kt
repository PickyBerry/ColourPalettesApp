package com.example.recyclerviewhw.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recyclerviewhw.data.PaletteItem
import com.example.recyclerviewhw.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//Viewmodel for second screen with favorite palettes
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    app: Application,
    private val repository: Repository
) : AndroidViewModel(app) {


    //Keeping favorite palettes in livedata
    private var _favorites = MutableLiveData<List<PaletteItem>>()
    val favorites: LiveData<List<PaletteItem>> = _favorites

    init {
        getFavorites()
    }

    //loading favorite palettes from repository
    private fun getFavorites() {
        _favorites.postValue(repository.getFavorites())
    }


    fun removeFavorite(paletteItem: PaletteItem) {
        repository.removeFavorite(paletteItem)
        getFavorites()
    }


}