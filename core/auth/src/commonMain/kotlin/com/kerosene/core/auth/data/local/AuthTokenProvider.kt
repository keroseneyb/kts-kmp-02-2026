package com.kerosene.core.auth.data.local

import com.kerosene.core.auth.api.TokenProvider
import com.kerosene.core.auth.domain.repository.AuthPrefs
import kotlinx.coroutines.flow.first

class AuthTokenProvider(private val authPrefs: AuthPrefs) : TokenProvider {

    override suspend fun getAccessToken(): String? = authPrefs.getSessionCookie().first()
    
    override suspend fun getRefreshToken(): String? = null
    
    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        authPrefs.saveSessionCookie(accessToken)
    }

    override suspend fun clearTokens() = authPrefs.clearSessionCookie()
}
