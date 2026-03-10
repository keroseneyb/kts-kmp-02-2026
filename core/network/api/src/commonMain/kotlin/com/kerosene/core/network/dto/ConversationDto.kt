package com.kerosene.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConversationDto(
    @SerialName("id") val id: String,
    @SerialName("user") val user: UserDto,
    @SerialName("last_message") val lastMessage: LastMessageDto? = null,
    @SerialName("date_updated") val dateUpdated: String,
    @SerialName("state") val state: ConversationStateDto? = null,
    @SerialName("channel") val channel: ChannelDto? = null
)

@Serializable
data class ChannelDto(
    @SerialName("_id") val id: String,
    @SerialName("kind") val kind: String,
    @SerialName("photo_url") val photoUrl: String? = null
)
