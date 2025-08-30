package com.laironlf.githubmobile.domain.repository

import com.laironlf.githubmobile.domain.entities.JsonFile
import com.laironlf.githubmobile.domain.entities.Repo
import com.laironlf.githubmobile.domain.entities.RepoDetails
import com.laironlf.githubmobile.domain.entities.UserInfo

interface GitHubRepository {
    suspend fun getRepositoryReadme(
        token: String,
        ownerName: String,
        repositoryName: String,
        branchName: String
    ): JsonFile

    suspend fun getRepository(
        token: String,
        repoId: String
    ): RepoDetails

    suspend fun getRepositories(
        token: String,
        userName: String
    ): List<Repo>

    suspend fun getUserInfo(
        token: String
    ): UserInfo

}