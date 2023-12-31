package com.example.nsc_events.data

import com.example.nsc_events.R
import com.example.nsc_events.model.Event
import com.example.nsc_events.model.SocialMedia
import java.util.Date

class Datasource {
    fun loadEvents(): List<Event> {
        // TODO: Replace with API call to get products from backend
        return listOf(
            Event("Thanksgiving Pageant", "A play put on by the acting department.", "fun",
                Date(20231103), "7PM", "9PM","Stage One Theater", R.drawable.happy_thanksgiving.toString(),
                "school", "www.nsc.com", "now", "100", "$50.00",
                arrayOf("thanks", "giving"), "all day", arrayOf("none"), "none", "no cancellations",
                "teacher", SocialMedia("my facebook","my twitter", "my instagram", "my hashtag"),
                "no privacy", "not very good", true, "65659e84e0686d0542e26a84"),
            Event("New Year's Day Celebration", "Meet with fellow students to celebrate the first day of the new year!", "fun",
                Date(20241201), "2PM", "4PM", "The Grove", R.drawable.newyears_png2.toString(),
                "school", "www.nsc.com", "now", "100", "$50.00",
                arrayOf("thanks", "giving"), "all day", arrayOf("none"), "none", "no cancellations",
                "teacher", SocialMedia("my facebook","my twitter", "my instagram", "my hashtag"),
                "no privacy", "not very good", true, "655f139d518629964f8e3d67"),
            Event("Increase the Peace Gathering", "Come together with other students to listen to a speech on world peace", "fun",
                Date(20240527), "4PM", "5PM", "Courtyard", R.drawable.speech_2759550__480_1_png.toString(),
                "school", "www.nsc.com", "now", "100", "$50.00",
                arrayOf("thanks", "giving"), "all day", arrayOf("none"), "none", "no cancellations",
                "teacher", SocialMedia("my facebook","my twitter", "my instagram", "my hashtag"),
                "no privacy", "not very good", true, "1"),
        )
    }
}