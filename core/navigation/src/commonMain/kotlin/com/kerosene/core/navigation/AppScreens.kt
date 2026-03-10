package com.kerosene.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object Greeting

@Serializable
object Login

@Serializable
object Feed

@Serializable
data class ConversationDetail(val conversationId: String)