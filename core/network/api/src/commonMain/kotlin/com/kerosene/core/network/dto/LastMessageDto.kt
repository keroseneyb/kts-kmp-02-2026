package com.kerosene.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LastMessageDto(
    @SerialName("text") val text: String? = null
)
