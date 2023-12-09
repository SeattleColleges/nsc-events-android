package com.example.nsc_events.model

import DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class SocialMedia(
    val facebook: String,
    val twitter: String,
    val instagram: String,
    val hashtag: String
)

@Serializable
data class Event(
    val eventTitle: String,
    val eventDescription: String,
    val eventCategory: String,
    // todo: question to ask, do we want to create a customer serializer for a Date type or just use a string type and format it as a date?
    @Serializable(with = DateSerializer::class)
    val eventDate: Date, // store date as a string in a specific date format matching backend date field
    val eventStartTime: String,
    val eventEndTime: String,
    val eventLocation: String,
    val eventCoverPhoto: String,
    val eventHost: String,
    val eventWebsite: String,
    val eventRegistration: String,
    val eventCapacity: String,
    val eventCost: String,
    val eventTags: Array<String>,
    val eventSchedule: String,
    val eventSpeakers: Array<String>,
    val eventPrerequisites: String,
    val eventCancellationPolicy: String,
    val eventContact: String,
    val eventSocialMedia: SocialMedia,
    val eventPrivacy: String,
    val eventAccessibility: String,
    var eventVisibility: Boolean,
    var id: String = "0",
){
    fun deleteEvent() {
        this.eventVisibility = false

    }
}