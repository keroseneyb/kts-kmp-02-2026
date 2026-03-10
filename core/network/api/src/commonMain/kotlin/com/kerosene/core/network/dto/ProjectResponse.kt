package com.kerosene.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectResponse(
    @SerialName("status") val status: String,
    @SerialName("data") val data: ProjectData? = null
)
