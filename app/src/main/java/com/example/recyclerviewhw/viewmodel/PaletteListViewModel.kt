package com.example.recyclerviewhw.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.recyclerviewhw.R
import com.example.recyclerviewhw.app.App
import com.example.recyclerviewhw.model.*
import com.example.recyclerviewhw.network.InternetValidation.hasInternetConnection
import com.example.recyclerviewhw.network.RetrofitInstance
import com.example.recyclerviewhw.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class PaletteListViewModel(
    app: Application,
    private val repository: Repository
) : AndroidViewModel(app) {

    private val paletteList = mutableListOf<PaletteItem>()

    fun addFavorite(paletteItem: PaletteItem) = repository.addFavorite(paletteItem)
    fun removeFavorite(paletteItem: PaletteItem) = repository.removeFavorite(paletteItem)


    fun paletteFlow(n: Int): Flow<Resource<MutableList<PaletteItem>>> {
        return flow {
            emit(Resource.Loading())
            try {
                if (hasInternetConnection(getApplication<App>())) {
                    repeat(n) {
                        paletteList.add(
                            PaletteItem(
                                handleResponse(repository.getPalettes()).data!!,
                                false
                            )
                        )
                    }
                    emit(Resource.Success(paletteList))

                } else {
                    emit(Resource.Error(getApplication<App>().getString(R.string.no_internet_connection)))
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> emit(
                        Resource.Error(
                            getApplication<App>().getString(
                                R.string.network_failure
                            )
                        )
                    )
                    else -> emit(
                        Resource.Error(
                            getApplication<App>().getString(
                                R.string.error
                            )
                        )
                    )
                }
            }
        }
    }

    fun getCurrentList() = paletteList


private fun handleResponse(response: Response<Palette>): Resource<Palette> {
    if (response.isSuccessful) {
        response.body()?.let { resultResponse ->
            return Resource.Success(resultResponse)
        }
    }
    return Resource.Error(response.message())
}


}

