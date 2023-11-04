package com.example.nsc_events.data

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
    /* TODO: change date variable to a Date data type instead of string */
    val eventCategory: String,
    val date: Date,
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
    val eventVisibility: Boolean


    /* TODO: what other event properties should be added? */
)
