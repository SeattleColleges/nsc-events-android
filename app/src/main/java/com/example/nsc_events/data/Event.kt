package com.example.nsc_events.data

data class Event(
    val eventName: String,
    val eventDescription: String,
    /* TODO: change date variable to a Date data type instead of string */
    val eventDate: String,
    val eventCategory: String,
    val eventStartTime: String,
    val eventEndTime: String,
    val eventLocation: String,
    val eventCoverPhoto: String,
    val eventHost: String,
    val eventWebsite: String,
    val eventRegistration: String,
    val eventCapacity: String,
    val eventCost: String,
    val eventTags: String = emptyArray(),
    val eventSchedule: String,
    val eventSpeakers: String = emptyArray(),
    val eventPrerequisites: String,
    val eventCancellationPolicy: String,
    val eventContact: String,
    val eventPrivacy: String,
    val eventAccessibility: String,
    val eventVisibility: String,



    /* TODO: what other event properties should be added? */
)
