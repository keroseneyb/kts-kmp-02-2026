package com.kerosene.core.auth.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun logout(): Result<Unit>
    fun getSessionCookie(): Flow<String?>
}