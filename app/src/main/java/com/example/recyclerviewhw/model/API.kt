package com.example.recyclerviewhw.model
import kotlinx.coroutines.Deferred
import retrofit2.Call;
import retrofit2.Response
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface API {
    @POST("./")
       suspend fun getPalette(@Body myCall: MyCall): Response<Palette>

}