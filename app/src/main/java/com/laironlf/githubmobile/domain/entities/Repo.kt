package com.laironlf.githubmobile.domain.entities

import com.laironlf.githubmobile.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repo(
    @SerialName("id") val id: Int,
    @SerialName("description") val description: String?,
    @SerialName("language") val language: String,
    @SerialName("name") val repoName: String
) {
    fun getColor(): Int {
        return LanguageColor.getColorByValue(language).color
    }

    enum class LanguageColor(val language: String, val color: Int){
        Kotlin("Kotlin", R.color.KotlinColor),
        Js("JavaScript", R.color.JavaScriptColor),
        Java("Java", R.color.JavaColor),
        CSharp("C#", R.color.CSharpColor),
        Default("default", R.color.KotlinColor);

        companion object{
            fun getColorByValue(value: String): LanguageColor {
                val color = entries.firstOrNull { it.language.lowercase() == value.lowercase() }
                if (color == null) {
                    return Default
                }
                return color
            }
        }
    }

}
