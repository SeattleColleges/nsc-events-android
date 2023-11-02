package com.example.nsc_events.data

import java.util.Date

data class Event(
    val eventName: String,
    val description: String,
    /* TODO: change date variable to a Date data type instead of string */
    val date: String,
    /* TODO: what other event properties should be added? */
    val startTime: String,
    val endTime: String,
)
