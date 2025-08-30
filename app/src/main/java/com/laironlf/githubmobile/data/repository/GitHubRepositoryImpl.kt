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
        token: String,
        ownerName: String,
        repositoryName: String,
        branchName: String
    ): JsonFile {
        return gitHubApiService.getRepositoryReadme(
            token = token,
            ownerName = ownerName,
            repositoryName = repositoryName,
            branchName = branchName
        )
    }

    override suspend fun getRepository(
        token: String,
        repoId: String,
    ): RepoDetails {
        return gitHubApiService.getRepository(token = "Bearer $token", repoId = repoId)
    }

    override suspend fun getRepositories(token: String, userName: String): List<Repo> {
        return gitHubApiService.getRepositories(token = "Bearer $token", userName = userName)
    }


    override suspend fun getUserInfo(token: String): UserInfo {
        return gitHubApiService.getUserInfo(token = "Bearer $token")
    }
}