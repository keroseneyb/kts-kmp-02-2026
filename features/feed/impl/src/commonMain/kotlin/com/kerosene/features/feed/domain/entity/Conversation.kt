package com.kerosene.features.feed.domain.entity

data class Conversation(
    val id: String,
    val userName: String,
    val lastMessage: String,
    val userPhotoUrl: String,
    val socialIconUrl: String?,
    val socialKind: String?,
    val dateUpdated: String,
    val displayTime: String,
    val isWaitingResponse: Boolean
)
