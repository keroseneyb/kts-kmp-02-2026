package com.kerosene.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectDto(
    @SerialName("_id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("photo_url") val photoUrl: String? = null
)
