package com.kerosene.core.network

interface CabinetProvider {
    suspend fun getCabinetId(): String?
    suspend fun getProjectId(): String?
    suspend fun saveSpace(cabinetId: String, projectId: String)
    suspend fun clearSpace()
}
