package com.laironlf.githubmobile.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.laironlf.githubmobile.data.interceptors.AuthInterceptor
import com.laironlf.githubmobile.data.services.GitHubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideOkkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGitHubApiService(okHttpClient: OkHttpClient): GitHubApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(GitHubApiService::class.java)
    }
}