package com.example.recyclerviewhw.network
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

//basic api interface
interface API {
    @POST("./")
       suspend fun getPalette(@Body myCall: MyCall): Response<PaletteResponse>

}