package com.example.nsc_events.data

import com.example.nsc_events.model.Event

class Datasource {
    fun loadEvents(): List<Event> {
        // TODO: Replace with API call to get products from backend
        return listOf(
            Event("Thanksgiving Pageant", "A play put on by the acting department.", "11/3/23", "7PM", "NSC Stage One Theater"),
            Event("New Year's Day Celebration", "Meet with fellow students to celebrate the first day of the new year!", "1/1/24", "2PM", "NSC College Center"),
            Event("Increase the Peace Gathering", "Come together with other students to listen to a speech on world peace", "5/7/24", "4PM", "NSC Courtyard"),
        )
    }
}