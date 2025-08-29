package com.laironlf.githubmobile.di

import com.laironlf.githubmobile.data.api.github.GitHubApiService
import com.laironlf.githubmobile.data.api.github.GitHubRepositoryImpl
import com.laironlf.githubmobile.domain.repository.AppRepository
import com.laironlf.githubmobile.domain.repository.GitHubRepository
import com.laironlf.githubmobile.domain.repository.KeyValueStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
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