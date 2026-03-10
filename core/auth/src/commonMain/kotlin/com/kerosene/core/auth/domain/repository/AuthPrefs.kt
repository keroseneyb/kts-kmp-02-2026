package com.kerosene.core.auth.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthPrefs {
    fun getSessionCookie(): Flow<String?>
    suspend fun saveSessionCookie(cookie: String)
    suspend fun clearSessionCookie()

    fun getCabinetId(): Flow<String?>
    fun getProjectId(): Flow<String?>
    suspend fun saveCabinet(cabinetId: String, projectId: String)
    suspend fun clearCabinet()
}