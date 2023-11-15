package com.example.nsc_events.model

import java.util.Date

data class SocialMedia(
    val facebook: String,
    val twitter: String,
    val instagram: String,
    val hashtag: String
)

data class Event(
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
    val eventTags: Array<String>,
    val eventSchedule: String,
    val eventSpeakers: Array<String>,
    val eventPrerequisites: String,
    val eventCancellationPolicy: String,
    val eventContact: String,
    val eventSocialMedia: SocialMedia,
    val eventPrivacy: String,
    val eventAccessibility: String,
    var eventVisibility : Boolean
){
    fun deleteEvent() {
        this.eventVisibility = false

    }
}