package com.laironlf.githubmobile.data.services

import com.laironlf.githubmobile.domain.entities.JsonFile
import com.laironlf.githubmobile.domain.entities.Repo
import com.laironlf.githubmobile.domain.entities.RepoDetails
import com.laironlf.githubmobile.domain.entities.UserInfo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {

    @GET("repos/{owner}/{repo}/readme")
    suspend fun getRepositoryReadme(
        @Header(API_AUTHORIZATION_KEY) token: String,
        @Path("owner") ownerName: String,
        @Path("repo") repositoryName: String,
        @Query("ref") branchName: String
    ): JsonFile

    @GET("repositories/{repoId}")
    suspend fun getRepository(
        @Header(API_AUTHORIZATION_KEY) token: String,
        @Path("repoId") repoId: String
    ): RepoDetails


    @GET("users/{owner}/repos")
    suspend fun getRepositories(
        @Header(API_AUTHORIZATION_KEY) token: String,
        @Path("owner") userName: String
    ): List<Repo>

    @GET("user")
    suspend fun getUserInfo(
        @Header(API_AUTHORIZATION_KEY) token: String
    ): UserInfo


    companion object {
        private const val API_AUTHORIZATION_KEY = "Authorization"
    }

}