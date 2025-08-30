package com.laironlf.githubmobile.domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDetails(
    @SerialName("id") val id: Int,
    @SerialName("name") val repoName: String,
    @SerialName("default_branch") val defaultBranch: String,
    @SerialName("forks_count") val forksCount: Int,
    @SerialName("watchers_count") val watchersCount: Int,
    @SerialName("stargazers_count") val starsCount: Int,
    @SerialName("html_url") val htmlUrl: String

)
