package com.example.recyclerviewhw.hilt


import android.content.Context
import com.example.recyclerviewhw.network.API
import com.example.recyclerviewhw.data.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//Dependency Injection hilt module for repository
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRepository(api: API, @ApplicationContext context: Context): Repository {
        return Repository(api, context)
    }

}