package com.example.cineapp.di.modules

import com.example.cineapp.common.data.local.datasource.LocalDataSource
import com.example.cineapp.common.data.local.datasource.LocalDataSourceImpl
import com.example.cineapp.common.data.remote.datasource.RemoteDataSource
import com.example.cineapp.common.data.remote.datasource.RemoteDataSourceImpl
import com.example.cineapp.common.utils.AndroidNetworkChecker
import com.example.cineapp.common.utils.NetworkChecker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataModule {

    @Binds
    fun bindNetworkChecker(impl: AndroidNetworkChecker): NetworkChecker

    @Binds
    fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

    @Binds
    fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource


}