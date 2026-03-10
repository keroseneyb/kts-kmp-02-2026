package com.kerosene.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConversationData(
    @SerialName("conversations") val conversations: List<ConversationDto>
)
