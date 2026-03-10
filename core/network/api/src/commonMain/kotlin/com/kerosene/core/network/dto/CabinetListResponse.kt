package com.kerosene.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CabinetListResponse(
    @SerialName("status") val status: String,
    @SerialName("data") val data: CabinetListData? = null
)
