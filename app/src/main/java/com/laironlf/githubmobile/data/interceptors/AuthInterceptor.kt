package com.laironlf.githubmobile.data.interceptors

import com.laironlf.githubmobile.data.storage.KeyValueStorage
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val keyValueStorage: KeyValueStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = if (!keyValueStorage.authToken.isNullOrEmpty()) {
            originalRequest.newBuilder()
                .header(API_AUTHORIZATION_KEY, "Bearer ${keyValueStorage.authToken}")
                .build()
        } else {
            originalRequest
        }
        return chain.proceed(newRequest)
    }

    companion object {
        private const val API_AUTHORIZATION_KEY = "Authorization"
    }
}