package com.example.nsc_events.data

import java.util.Date

data class Event(
    val eventTitle: String,
    val eventDescription: String,
    /* TODO: change date variable to a Date data type instead of string */
    val eventDate: String,
    var eventVisibility : Boolean
){
    fun deleteEvent() {
        this.eventVisibility = false

    }
}

