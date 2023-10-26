package com.laironlf.githubmobile.data.api.github

import com.laironlf.githubmobile.domain.entities.JsonFile
import com.laironlf.githubmobile.domain.entities.Repo
import com.laironlf.githubmobile.domain.entities.RepoDetails
import com.laironlf.githubmobile.domain.entities.UserInfo
import com.laironlf.githubmobile.domain.repository.GitHubRepository
import retrofit2.Response
import retrofit2.http.HTTP

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