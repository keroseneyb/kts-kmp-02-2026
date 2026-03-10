package com.kerosene.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CabinetListData(
    @SerialName("cabinets") val cabinets: List<CabinetDto> = emptyList()
)
