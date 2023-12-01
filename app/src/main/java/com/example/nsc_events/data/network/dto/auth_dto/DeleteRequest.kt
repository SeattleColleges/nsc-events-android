package com.example.nsc_events.data.network.dto.auth_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteRequest(
    @SerialName("id")
    val id: String,

    @SerialName("role")
    val role: Role
)
