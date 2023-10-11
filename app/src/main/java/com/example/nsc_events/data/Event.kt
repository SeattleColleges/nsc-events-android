package com.example.nsc_events.data

import java.util.Date

data class SocialMedia(
    val facebook: String,
    val twitter: String,
    val instagram: String,
    val hashtag: String
)
data class Event(
    val createdByUser: String, // Assuming it's a user ID or username
    val eventTitle: String,
    val eventDescription: String,
    val eventCategory: String,
    val eventDate: Date,
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
    val eventSocialMedia: SocialMedia,
    val eventPrivacy: String,
    val eventAccessibility: String,
    val eventVisibility: String

    /* TODO: what other event properties should be added? */
)
