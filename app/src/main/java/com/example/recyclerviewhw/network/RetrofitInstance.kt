package com.example.recyclerviewhw.network

import com.example.recyclerviewhw.model.API
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance {
    companion object {

        private const val BASE_URL = "http://colormind.io/api/"

        private val retrofit by lazy {
            val client = OkHttpClient.Builder()
                .addInterceptor(RequestInterceptor)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val listApi by lazy {
            retrofit.create(API::class.java)
        }
    }
}