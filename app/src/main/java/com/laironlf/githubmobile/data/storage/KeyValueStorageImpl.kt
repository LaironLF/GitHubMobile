package com.laironlf.githubmobile.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.laironlf.githubmobile.domain.entities.UserInfo
import com.laironlf.githubmobile.domain.repository.KeyValueStorage

class KeyValueStorageImpl(context: Context) : KeyValueStorage() {
    override var authToken: String?
        get() = getToken()
        set(value) = saveToken(value)
    override var userInfo: UserInfo?
        get() = getUser()
        set(value) = saveUser(value)


    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_STORAGE_NAME, Context.MODE_PRIVATE)

    private fun getToken(): String? = sharedPrefs.getString(SHARED_PREFS_TOKEN_VALUE_KEY, null)

    private fun saveToken(token: String?) =
        sharedPrefs.edit().putString(SHARED_PREFS_TOKEN_VALUE_KEY, token).apply()

    private fun getUser(): UserInfo? {
        val userName = sharedPrefs.getString(SHARED_PREFS_USER_LOGIN_VALUE_KEY, null) ?: return null
        return UserInfo(userName)
    }

    private fun saveUser(userInfo: UserInfo?) {
        sharedPrefs.edit().putString(SHARED_PREFS_USER_LOGIN_VALUE_KEY, userInfo?.login).apply()
    }

    companion object {
        private const val SHARED_PREFS_STORAGE_NAME = "gitHub_data"
        private const val SHARED_PREFS_TOKEN_VALUE_KEY = "token_value"
        private const val SHARED_PREFS_USER_LOGIN_VALUE_KEY = "user_login"
    }
}