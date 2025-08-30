package com.laironlf.githubmobile.domain.repository

import android.util.Base64
import com.laironlf.githubmobile.data.storage.KeyValueStorage
import com.laironlf.githubmobile.domain.entities.JsonFile
import com.laironlf.githubmobile.domain.entities.Repo
import com.laironlf.githubmobile.domain.entities.RepoDetails
import java.nio.charset.StandardCharsets

class AppRepository(
    private val keyValueStorage: KeyValueStorage, private val gitHubRepository: GitHubRepository
) {
    suspend fun getRepositories(): List<Repo> {
        return gitHubRepository.getRepositories(userName = keyValueStorage.userInfo!!.login)
    }

    suspend fun getRepository(repoId: String): RepoDetails {
        return gitHubRepository.getRepository(repoId = repoId)
    }


    suspend fun getRepositoryReadme(
        ownerName: String,
        repositoryName: String,
        branchName: String
    ): String {
        val jsonFile: JsonFile = gitHubRepository.getRepositoryReadme(
            ownerName = ownerName,
            repositoryName = repositoryName,
            branchName = branchName
        )
        return Base64.decode(jsonFile.content, Base64.DEFAULT).toString(StandardCharsets.UTF_8)
    }

    suspend fun getRepositoryReadme(
        repoDetails: RepoDetails
    ): String {
        return getRepositoryReadme(
            repositoryName = repoDetails.repoName,
            branchName = repoDetails.defaultBranch,
            ownerName = keyValueStorage.userInfo!!.login
        )
    }

    suspend fun signInByStorageToken(): Boolean {
        return try {
            gitHubRepository.getUserInfo()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            clearLoginData()
            false
        }
    }

    suspend fun signIn(token: String) {
        keyValueStorage.authToken = token
        keyValueStorage.userInfo = gitHubRepository.getUserInfo()
    }

    fun clearLoginData() {
        keyValueStorage.userInfo = null
        keyValueStorage.authToken = null
    }

}