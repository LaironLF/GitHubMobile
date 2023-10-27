package com.laironlf.githubmobile.data.api.github

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class GitHubRetrofitInstance {
    private val retrofit: Retrofit by lazy {
        val json = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val api: GitHubApiService by lazy {
        retrofit.create(GitHubApiService::class.java)
    }

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }
}