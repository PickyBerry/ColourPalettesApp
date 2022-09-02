package com.example.recyclerviewhw.model
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface API {
    @POST("./")
       suspend fun getPalette(@Body myCall: MyCall): Response<Palette>

}