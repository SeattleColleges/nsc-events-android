package com.example.nsc_events.data

import com.example.nsc_events.R
import com.example.nsc_events.data.Event
// question I added a datasource to mock data but is this used in the HttpRoutes?
class Datasource {
    private val eventList = mutableListOf<Event>()

    init {
        eventList.add(
            Event("Halloween Event", "Halloween Description", "10/31/2023")
        )
        eventList.add(
            Event("Christmas Event", "Christmas Description", "12/25/2023")
        )
        eventList.add(
            Event("New Year's Eve Event", "New Year's Eve Description", "12/31/2023")
        )
        eventList.add(
            Event("Valentine's Day Event", "Valentine's Day Description", "02/14/2024")
        )
        eventList.add(
            Event("Easter Event", "Easter Description", "04/07/2024")
        )
    }

    fun loadEvents(): List<Event> {
        return eventList
    }
}