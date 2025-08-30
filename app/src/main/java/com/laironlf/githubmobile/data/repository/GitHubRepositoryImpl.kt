package com.laironlf.githubmobile.data.repository

import com.laironlf.githubmobile.data.services.GitHubApiService
import com.laironlf.githubmobile.domain.entities.JsonFile
import com.laironlf.githubmobile.domain.entities.Repo
import com.laironlf.githubmobile.domain.entities.RepoDetails
import com.laironlf.githubmobile.domain.entities.UserInfo
import com.laironlf.githubmobile.domain.repository.GitHubRepository

class GitHubRepositoryImpl(
    private val gitHubApiService: GitHubApiService
) : GitHubRepository {
    override suspend fun getRepositoryReadme(
        ownerName: String,
        repositoryName: String,
        branchName: String
    ): JsonFile {
        return gitHubApiService.getRepositoryReadme(
            ownerName = ownerName,
            repositoryName = repositoryName,
            branchName = branchName
        )
    }

    override suspend fun getRepository(repoId: String): RepoDetails {
        return gitHubApiService.getRepository(repoId = repoId)
    }

    override suspend fun getRepositories(userName: String): List<Repo> {
        return gitHubApiService.getRepositories(userName = userName)
    }


    override suspend fun getUserInfo(): UserInfo {
        return gitHubApiService.getUserInfo()
    }
}