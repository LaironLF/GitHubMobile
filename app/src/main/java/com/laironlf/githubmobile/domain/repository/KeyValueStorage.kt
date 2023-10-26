package com.laironlf.githubmobile.domain.repository

import com.laironlf.githubmobile.domain.entities.UserInfo

open class KeyValueStorage {
    open var authToken: String? = null
    open var userInfo: UserInfo? = null
}