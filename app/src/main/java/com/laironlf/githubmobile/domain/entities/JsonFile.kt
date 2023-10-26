package com.laironlf.githubmobile.domain.entities


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonFile(
    @SerialName("content") val content: String?,
    @SerialName("encoding") val encoding: String?,
    @SerialName("name") val name: String?,
    @SerialName("path") val path: String?,
    @SerialName("sha") val sha: String?,
    @SerialName("size") val size: Int?,
    @SerialName("type") val type: String?
)