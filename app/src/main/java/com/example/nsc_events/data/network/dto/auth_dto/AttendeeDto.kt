package com.example.nsc_events.data.network.dto.auth_dto

import kotlinx.serialization.Serializable

@Serializable
data class AttendeeDto(val attendee: Attendee) {
    @Serializable
    data class Attendee(
        val firstName: String?,
        val lastName: String?
    )
}