package com.kerosene.core.auth.data.repository

import com.kerosene.core.auth.domain.repository.AuthPrefs
import com.kerosene.core.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val authPrefs: AuthPrefs
) : AuthRepository {

    override suspend fun logout(): Result<Unit> {
        return runCatching {
            authPrefs.clearSessionCookie()
        }
    }

    override fun getSessionCookie(): Flow<String?> =
        authPrefs.getSessionCookie()
}
