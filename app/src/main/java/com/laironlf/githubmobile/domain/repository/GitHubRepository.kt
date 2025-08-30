package com.laironlf.githubmobile.domain.repository

import com.laironlf.githubmobile.domain.entities.JsonFile
import com.laironlf.githubmobile.domain.entities.Repo
import com.laironlf.githubmobile.domain.entities.RepoDetails
import com.laironlf.githubmobile.domain.entities.UserInfo

interface GitHubRepository {
    suspend fun getRepositoryReadme(
        ownerName: String,
        repositoryName: String,
        branchName: String
    ): JsonFile

    suspend fun getRepository(
        repoId: String
    ): RepoDetails

    suspend fun getRepositories(
        userName: String
    ): List<Repo>

    suspend fun getUserInfo(): UserInfo

}