package com.example.recyclerviewhw.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.recyclerviewhw.R
import com.example.recyclerviewhw.app.App
import com.example.recyclerviewhw.model.*
import com.example.recyclerviewhw.network.InternetValidation.hasInternetConnection
import com.example.recyclerviewhw.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class PaletteListViewModel(
    app: Application,
    private val repository: Repository
) : AndroidViewModel(app) {

    private val paletteList = mutableListOf<PaletteItem>()
    val palettes = MutableLiveData<Resource<List<PaletteItem>>>()


    init {
        getPalettes()
      //  getPalettes()
    }

    fun getPalettes() = viewModelScope.launch {
        fetchPalettes(20)
    }

    fun addFavorite(paletteItem: PaletteItem) = repository.addFavorite(paletteItem)
    fun removeFavorite(paletteItem: PaletteItem) = repository.removeFavorite(paletteItem)

    private suspend fun fetchPalettes(n: Int) {
        palettes.postValue(Resource.Loading())

        try {
            if (hasInternetConnection(getApplication<App>())) {
                repeat(n) {
                    val response = repository.getPalettes()
                    paletteList.add(PaletteItem(handleResponse(response).data!!,false))
                }
                palettes.postValue(Resource.Success(paletteList))

            } else {
                palettes.postValue(Resource.Error(getApplication<App>().getString(R.string.no_internet_connection)))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> palettes.postValue(
                    Resource.Error(
                        getApplication<App>().getString(
                            R.string.network_failure
                        )
                    )
                )
                else -> palettes.postValue(
                    Resource.Error(
                        getApplication<App>().getString(
                            R.string.error
                        )
                    )
                )
            }
        }
    }

    private fun handleResponse(response: Response<Palette>): Resource<Palette> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}

