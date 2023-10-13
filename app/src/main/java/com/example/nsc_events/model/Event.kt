package com.example.nsc_events.model

data class Event(
    val eventTitle: String,
    val eventDescription: String,
    /* TODO: change date variable to a Date data type instead of string */
    val eventDate: String,
    /* TODO: what other event properties should be added? */
    val eventStartTime: String,
)
