package com.laironlf.githubmobile.domain.entities

import android.graphics.Color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repo(
    @SerialName("id") val id: Int,
    @SerialName("description") val description: String?,
    @SerialName("language") val language: String?,
    @SerialName("name") val repoName: String
) {
    fun getColor(): Int {
        return Color.parseColor(colors.getOrDefault(language, "#A37AEE"))
    }

    companion object {
        private val colors: Map<String, String> = mapOf(
            "Kotlin" to "#A37AEE",
            "JavaScript" to "#F4E264",
            "Java" to "#B07219",
            "C#" to "#5989FA"
        )
    }
}
