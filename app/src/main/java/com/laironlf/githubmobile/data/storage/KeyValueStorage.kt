package com.laironlf.githubmobile.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.laironlf.githubmobile.domain.entities.UserInfo
import javax.inject.Inject

class KeyValueStorage @Inject constructor(
    private val sharedPrefs: SharedPreferences
) {
    var authToken: String?
        get() = getToken()
        set(value) = saveToken(value)
    var userInfo: UserInfo?
        get() = getUser()
        set(value) = saveUser(value)


    private fun getToken(): String? = sharedPrefs.getString(SHARED_PREFS_TOKEN_VALUE_KEY, null)

    private fun saveToken(token: String?) =
        sharedPrefs.edit { putString(SHARED_PREFS_TOKEN_VALUE_KEY, token) }

    private fun getUser(): UserInfo? {
        val userName = sharedPrefs.getString(SHARED_PREFS_USER_LOGIN_VALUE_KEY, null) ?: return null
        return UserInfo(userName)
    }

    private fun saveUser(userInfo: UserInfo?) {
        sharedPrefs.edit { putString(SHARED_PREFS_USER_LOGIN_VALUE_KEY, userInfo?.login) }
    }

    companion object {
        const val SHARED_PREFS_STORAGE_NAME = "gitHub_data"
        const val SHARED_PREFS_TOKEN_VALUE_KEY = "token_value"
        const val SHARED_PREFS_USER_LOGIN_VALUE_KEY = "user_login"
    }
}