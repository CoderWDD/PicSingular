package com.example.picsingular.common.di

import com.example.picsingular.common.utils.retrofit.RetrofitClient
import com.example.picsingular.service.network.CommentNetworkService
import com.example.picsingular.service.network.SingularNetworkService
import com.example.picsingular.service.network.UserNetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideUserService(): UserNetworkService = RetrofitClient.retrofit.create(UserNetworkService::class.java)

    @Provides
    @Singleton
    fun provideSingularService(): SingularNetworkService = RetrofitClient.retrofit.create(SingularNetworkService::class.java)

    @Provides
    @Singleton
    fun provideCommentService(): CommentNetworkService = RetrofitClient.retrofit.create(CommentNetworkService::class.java)
}