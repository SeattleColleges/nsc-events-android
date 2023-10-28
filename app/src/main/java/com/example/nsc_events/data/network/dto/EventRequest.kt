package com.example.nsc_events.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventRequest(
    val createdByUserId: String,
    val eventTitle: String,
    val eventDescription: String,
    val eventCategory: String,
    val eventDate: String,
    val eventStartTime: String,
    val eventEndTime: String,
    val eventLocation: String,
    val eventCoverPhoto: String,
    val eventHost: String,
    val eventWebsite: String,
    val eventRegistration: String,
    val eventCapacity: String,
    val eventCost: String,
    val eventTags: List<String>,
    val eventSchedule: String,
    val eventSpeakers: List<String>,
    val eventPrerequisites: String,
    val eventCancellationPolicy: String,
    val eventContact: String,
    val eventSocialMedia: Map<String, String>,
    val eventPrivacy: String,
    val eventAccessibility: String
)

