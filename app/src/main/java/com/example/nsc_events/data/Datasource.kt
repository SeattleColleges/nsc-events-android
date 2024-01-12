package com.example.nsc_events.data

import com.example.nsc_events.R
import com.example.nsc_events.data.network.auth.EventsService
import com.example.nsc_events.model.Event
import com.example.nsc_events.model.SocialMedia
import java.util.Date

class Datasource {
    private val eventsApiService: EventsService = EventsService.create()
    suspend fun loadEvents(): List<Event> {

        // TODO: Replace with API call to get products from backend
        val apiEvents = try {
            eventsApiService.getEvents()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<Event>()
        }

        return apiEvents
    }
}