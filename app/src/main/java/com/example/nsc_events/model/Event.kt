package com.example.nsc_events.model

data class Event(
    val eventTitle: String,
    val eventDescription: String,
    /* TODO: change date variable to a Date data type instead of string */
    val eventDate: String,
    val eventStartTime: String,
    val eventEndTime: String,
    val eventLocation: String,
    val eventCoverPhoto: String,
    var eventVisibility : Boolean,
){
    fun deleteEvent() {
        this.eventVisibility = false

    }
}