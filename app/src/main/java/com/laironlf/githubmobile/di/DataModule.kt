package com.laironlf.githubmobile.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.laironlf.githubmobile.data.api.github.GitHubApiService
import com.laironlf.githubmobile.data.api.github.GitHubRepositoryImpl
import com.laironlf.githubmobile.data.storage.KeyValueStorageImpl
import com.laironlf.githubmobile.domain.repository.AppRepository
import com.laironlf.githubmobile.domain.repository.GitHubRepository
import com.laironlf.githubmobile.domain.repository.KeyValueStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideKeyValueStorage(@ApplicationContext context: Context): KeyValueStorage {
        return KeyValueStorageImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideGitHubApiService(): GitHubApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(GitHubApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        keyValueStorage: KeyValueStorage,
        gitHubRepository: GitHubRepository
    ): AppRepository {
        return AppRepository(keyValueStorage = keyValueStorage, gitHubRepository = gitHubRepository)
    }

    @Provides
    @Singleton
    fun provideGitHubRepository(
        gitHubApiService: GitHubApiService
    ): GitHubRepository {
        return GitHubRepositoryImpl(gitHubApiService)
    }

}