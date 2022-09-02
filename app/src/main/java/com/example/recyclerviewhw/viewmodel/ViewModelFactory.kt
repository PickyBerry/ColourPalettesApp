package com.example.recyclerviewhw.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerviewhw.repository.Repository


class ViewModelFactory(
    val app: Application,
    val repository: Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaletteListViewModel::class.java)) {
            return PaletteListViewModel(app, repository) as T
        }
        else if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(app, repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

