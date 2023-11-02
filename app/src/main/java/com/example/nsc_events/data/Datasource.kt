package com.example.nsc_events.data

import com.example.nsc_events.R
import com.example.nsc_events.model.Event

class Datasource {
    fun loadEvents(): List<Event> {
        // TODO: Replace with API call to get products from backend
        return listOf(
            Event("Thanksgiving Pageant", "A play put on by the acting department.", "11/3/23", "7PM", "9PM","Stage One Theater", R.drawable.happy_thanksgiving.toString(), true),
            Event("New Year's Day Celebration", "Meet with fellow students to celebrate the first day of the new year!", "1/1/24", "2PM", "4PM", "The Grove", R.drawable.newyears_png2.toString(), true),
            Event("Increase the Peace Gathering", "Come together with other students to listen to a speech on world peace", "5/7/24", "4PM", "5PM", "Courtyard", R.drawable.speech_2759550__480_1_png.toString(), true),
        )
    }
}