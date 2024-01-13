package com.example.nsc_events.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class SocialMedia(
    val facebook: String?,
    val twitter: String?,
    val instagram: String?,
    val hashtag: String?
)
@Serializable
data class Attendee(
    val firstName: String? = null,
    val lastName: String? = null,
    @SerialName("_id") val id: String
)

@Serializable
data class Event(
    @SerialName("_id") val eventId: String,
    @SerialName("__v") val version: Int? = null,
    @SerialName("attendanceCount") val count: Int,
    val attendees: List<Attendee>,
    val updatedAt: String,
    val createdByUser: String,
    val createdAt: String,
    val eventTitle: String,
    val eventDescription: String,
    val eventCategory: String,
    val eventDate: String,
    val eventStartTime: String,
    val eventEndTime: String,
    val eventLocation: String,
    val eventCoverPhoto: String,
    val eventHost: String,
    val eventWebsite: String?,
    val eventRegistration: String,
    val eventCapacity: String,
    val eventCost: String,
    val eventTags: List<String>,
    val eventSchedule: String,
    val eventSpeakers: List<String>,
    val eventPrerequisites: String?,
    val eventCancellationPolicy: String?,
    val eventContact: String, // If this is supposed to be an email, it should be a String
    val eventSocialMedia: SocialMedia?,
    val eventPrivacy: String?,
    val eventAccessibility: String,
    @SerialName("isHidden") var eventVisibility: Boolean
) {
    val id: String = eventId
}