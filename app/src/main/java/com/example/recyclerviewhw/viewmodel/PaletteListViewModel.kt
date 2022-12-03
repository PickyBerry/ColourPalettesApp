package com.example.recyclerviewhw.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.recyclerviewhw.R
import com.example.recyclerviewhw.app.App
import com.example.recyclerviewhw.data.*
import com.example.recyclerviewhw.network.InternetValidation.hasInternetConnection
import com.example.recyclerviewhw.network.PaletteResponse
import com.example.recyclerviewhw.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


//Viewmodel for main screen with palettes list
@HiltViewModel
class PaletteListViewModel @Inject constructor(
    app: Application,
    private val repository: Repository
) : AndroidViewModel(app) {

    private val paletteList = mutableListOf<PaletteItem>()

    fun addFavorite(paletteItem: PaletteItem) = repository.addFavorite(paletteItem)
    fun removeFavorite(paletteItem: PaletteItem) = repository.removeFavorite(paletteItem)


    //Emitting palettes as flow
    fun paletteFlow(n: Int): Flow<Resource<MutableList<PaletteItem>>> {
        return flow {
            emit(Resource.Loading())
            try {
                if (hasInternetConnection(getApplication<App>())) {
                    repeat(n) {
                        paletteList.add(
                            PaletteItem(
                                handleResponse(repository.getPalettes()).data!!.toPalette(),
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

    //Handling the response from api
    private fun handleResponse(response: Response<PaletteResponse>): Resource<PaletteResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}

