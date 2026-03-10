package com.kerosene.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CabinetDto(
    @SerialName("_id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("domain") val domain: String? = null
)
