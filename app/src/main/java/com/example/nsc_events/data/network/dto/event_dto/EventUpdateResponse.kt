package com.example.nsc_events.data.network.dto.event_dto

import com.example.nsc_events.model.Event
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventUpdateResponse(
    @SerialName("updatedActivity")
    val updatedActivity: Event, //  data class representing an activity

    @SerialName("message")
    val message: String
)
