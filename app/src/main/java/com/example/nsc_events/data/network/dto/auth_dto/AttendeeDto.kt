package com.example.nsc_events.data.network.dto.auth_dto

import kotlinx.serialization.Serializable

@Serializable
data class AttendeeDto(
    val firstName: String?,
    val lastName: String?
)
