package com.example.cineapp.di.modules

import com.example.cineapp.common.data.remote.RetrofitClient
import com.example.cineapp.common.data.remote.service.MovieService
import com.example.cineapp.di.annotations.DispatcherIO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesRetrofit(): Retrofit {
        return RetrofitClient.retrofit
    }

    @Provides
    fun providesService(retrofit: Retrofit): MovieService{
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    @DispatcherIO
    fun providesDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}