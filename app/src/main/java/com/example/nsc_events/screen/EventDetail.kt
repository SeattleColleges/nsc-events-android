package com.example.nsc_events.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nsc_events.data.Event

@Composable
fun DeleteButton(
    event: Event,
    onDelete: (Event) -> Unit
) {
    Button(
        onClick = {
            event.deleteEvent()
            onDelete(event)
        },
        modifier = Modifier
            .padding(16.dp)
            .width(200.dp)
    ) {
        Text(text = "Delete Event")
    }
}