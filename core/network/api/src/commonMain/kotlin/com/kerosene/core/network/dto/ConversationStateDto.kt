package com.kerosene.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConversationStateDto(
    @SerialName("has_unanswered_operator_message") val hasUnansweredOperatorMessage: Boolean = false,
    @SerialName("stopped_by_manager") val stoppedByManager: Boolean = false
)
