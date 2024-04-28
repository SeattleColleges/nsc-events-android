package com.example.nsc_events.data.network.dto.event_dto

import DateSerializer
import java.util.Date
import com.example.nsc_events.model.SocialMedia
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventRequest(
    @SerialName("eventTitle")
    val eventTitle: String,

    @SerialName("eventDescription")
    val eventDescription: String,

    @SerialName("eventCategory")
    val eventCategory: String,

    @SerialName("eventDate")
    @Serializable(with = DateSerializer::class)
    // todo: question to ask, do we want to create a customer serializer for a Date type or just use a string type and format it as a date?
    val eventDate: Date, // Date as String for simplicity, change to Date

    @SerialName("eventStartTime")
    val eventStartTime: String,

    @SerialName("eventEndTime")
    val eventEndTime: String,

    @SerialName("eventLocation")
    val eventLocation: String,

    @SerialName("eventCoverPhoto")
    val eventCoverPhoto: String?,

    @SerialName("eventHost")
    val eventHost: String,

    @SerialName("eventMeetingURL")
    val eventMeetingURL: String,

    @SerialName("eventRegistration")
    val eventRegistration: String,

    @SerialName("eventCapacity")
    val eventCapacity: String,

    @SerialName("eventTags")
    val eventTags: List<String>,

    @SerialName("eventSchedule")
    val eventSchedule: String?,

    @SerialName("eventSpeakers")
    val eventSpeakers: Array<String>?,

    @SerialName("eventPrerequisites")
    val eventPrerequisites: String?,

    @SerialName("eventCancellationPolicy")
    val eventCancellationPolicy: String?,

    @SerialName("eventContact")
    val eventContact: String,

    @SerialName("eventSocialMedia")
    val eventSocialMedia: SocialMedia?, // Define SocialMedia class based on your schema

    @SerialName("eventPrivacy")
    val eventPrivacy: String?,

    @SerialName("eventAccessibility")
    val eventAccessibility: String,

    @SerialName("eventNote")
    val eventNote: String?,

    @SerialName("isHidden")
    val isHidden: Boolean?

)