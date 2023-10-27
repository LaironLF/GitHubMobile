package com.laironlf.githubmobile.di

import android.content.Context
import com.laironlf.githubmobile.data.api.github.GitHubApiService
import com.laironlf.githubmobile.data.api.github.GitHubRepositoryImpl
import com.laironlf.githubmobile.data.api.github.GitHubRetrofitInstance
import com.laironlf.githubmobile.domain.repository.KeyValueStorage
import com.laironlf.githubmobile.data.storage.KeyValueStorageImpl
import com.laironlf.githubmobile.domain.repository.AppRepository
import com.laironlf.githubmobile.domain.repository.GitHubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideKeyValueStorage(@ApplicationContext context: Context): KeyValueStorage {
        return KeyValueStorageImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideGitHubApiService(): GitHubApiService {
        return GitHubRetrofitInstance().api
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