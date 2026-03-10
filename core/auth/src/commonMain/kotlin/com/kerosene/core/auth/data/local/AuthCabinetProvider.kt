package com.kerosene.core.auth.data.local

import com.kerosene.core.auth.domain.repository.AuthPrefs
import com.kerosene.core.network.CabinetProvider
import kotlinx.coroutines.flow.first

class AuthCabinetProvider(private val authPrefs: AuthPrefs) : CabinetProvider {

    override suspend fun getCabinetId(): String? = authPrefs.getCabinetId().first()

    override suspend fun getProjectId(): String? = authPrefs.getProjectId().first()

    override suspend fun saveSpace(cabinetId: String, projectId: String) =
        authPrefs.saveCabinet(cabinetId, projectId)

    override suspend fun clearSpace() = authPrefs.clearCabinet()
}
