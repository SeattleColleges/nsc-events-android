package com.example.nsc_events.data.network.dto.auth_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteResponse(
    @SerialName("isHidden")
    val isHidden: Boolean = false,
)