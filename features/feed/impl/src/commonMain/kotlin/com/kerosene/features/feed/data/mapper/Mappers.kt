package com.kerosene.features.feed.data.mapper

import com.kerosene.core.network.dto.CabinetDto
import com.kerosene.core.network.dto.ConversationDto
import com.kerosene.core.network.dto.ProjectDto
import com.kerosene.features.feed.domain.entity.Conversation
import com.kerosene.features.feed.domain.entity.Cabinet

private const val DEFAULT_FAVICON = "https://www.smartbotpro.ru/favicon.ico"
private const val DEFAULT_PROJECT_NAME = "Основной проект"
private const val DEFAULT_USER_NAME = "Аноним"
private const val DEFAULT_LAST_MESSAGE = "Нет сообщений"

fun CabinetDto.toCabinet(): Cabinet {
    return Cabinet(
        id = id,
        name = DEFAULT_PROJECT_NAME,
        cabinetId = id,
        cabinetName = name,
        projectId = id,
        imageUrl = DEFAULT_FAVICON
    )
}

fun ProjectDto.toCabinet(cabinetId: String, cabinetName: String): Cabinet {
    return Cabinet(
        id = id,
        name = name,
        cabinetId = cabinetId,
        cabinetName = cabinetName,
        projectId = id,
        imageUrl = photoUrl ?: DEFAULT_FAVICON
    )
}

fun ConversationDto.toConversation(): Conversation {
    val displayTime = dateUpdated?.let {
        try {
            it.substringAfter('T').take(5)
        } catch (e: Exception) {
            it.take(10)
        }
    } ?: ""

    return Conversation(
        id = id,
        userName = user.firstName ?: user.username ?: DEFAULT_USER_NAME,
        lastMessage = lastMessage?.text ?: DEFAULT_LAST_MESSAGE,
        userPhotoUrl = user.photo?.url ?: DEFAULT_FAVICON,
        socialIconUrl = channel?.photoUrl,
        socialKind = channel?.kind,
        dateUpdated = dateUpdated ?: "",
        displayTime = displayTime,
        isWaitingResponse = state?.hasUnansweredOperatorMessage == true && state?.stoppedByManager == false
    )
}
