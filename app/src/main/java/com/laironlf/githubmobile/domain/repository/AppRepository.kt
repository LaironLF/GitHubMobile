package com.laironlf.githubmobile.domain.repository

import android.util.Base64
import com.laironlf.githubmobile.domain.entities.JsonFile
import com.laironlf.githubmobile.domain.entities.Repo
import com.laironlf.githubmobile.domain.entities.RepoDetails
import com.laironlf.githubmobile.domain.entities.UserInfo
import java.nio.charset.StandardCharsets

class AppRepository(
    private val keyValueStorage: KeyValueStorage, private val gitHubRepository: GitHubRepository
) {
    suspend fun getRepositories(): List<Repo> {
        return gitHubRepository.getRepositories(
            token = keyValueStorage.authToken!!, userName = keyValueStorage.userInfo!!.login
        )
    }

    suspend fun getRepository(repoId: String): RepoDetails {
        return gitHubRepository.getRepository(
            token = keyValueStorage.authToken!!, repoId = repoId
        )
    }


    suspend fun getRepositoryReadme(
        ownerName: String,
        repositoryName: String,
        branchName: String
    ): String {
        val jsonFile: JsonFile = gitHubRepository.getRepositoryReadme(
            token = keyValueStorage.authToken!!,
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
            branchName = repoDetails.branchName,
            ownerName = keyValueStorage.userInfo!!.login
        )
    }

    suspend fun signInByStorageToken(): Boolean {
        return try {
            signIn(token = keyValueStorage.authToken!!)
            true
        } catch (e: Exception) {
            clearLoginData()
            false
        }
    }

    suspend fun signIn(token: String): UserInfo {
        val userInfo: UserInfo = gitHubRepository.getUserInfo(token)
        updateKeyStorage(userInfo = userInfo, token = token)
        return userInfo
    }

    fun clearLoginData() {
        updateKeyStorage(null, null)
    }

    private fun updateKeyStorage(userInfo: UserInfo?, token: String?) {
        keyValueStorage.userInfo = userInfo
        keyValueStorage.authToken = token
    }

}