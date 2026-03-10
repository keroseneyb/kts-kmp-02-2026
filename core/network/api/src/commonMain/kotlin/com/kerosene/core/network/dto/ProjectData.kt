package com.kerosene.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectData(
    @SerialName("project") val project: ProjectDto? = null,
    @SerialName("projects") val projects: List<ProjectDto>? = null
)
