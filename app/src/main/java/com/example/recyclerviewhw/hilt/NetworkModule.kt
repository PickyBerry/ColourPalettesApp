package com.example.recyclerviewhw.hilt

import com.example.recyclerviewhw.network.API
import com.example.recyclerviewhw.network.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


//Dependency Injection hilt module for retrofit and api
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    //api URL
    private val BASE_URL = "http://colormind.io/api/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(RequestInterceptor)
                    .build()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideListAPI(retrofit: Retrofit): API {
        return retrofit.create(API::class.java)
    }
}