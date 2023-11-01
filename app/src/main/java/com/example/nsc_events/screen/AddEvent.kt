package com.example.nsc_events.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nsc_events.Routes
import com.example.nsc_events.data.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventPage(navController: NavHostController) {
    var eventTitle by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var eventVisibility by remember { mutableStateOf(true) }


    /* navigating back to login page */
    TopAppBar(
        title = { Text("Login") }, /* todo: change destination where arrow navigates to */
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.Login.route)
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EventInfoField(
            eventTitle = eventTitle,
            onEventTitleChange = { newEvent -> eventTitle = newEvent }
        )

        EventDescriptionField(
            eventDescription = eventDescription,
            onEventDescriptionChange = { newEventDescription ->
                eventDescription = newEventDescription
            }
        )

        EventDateField(
            eventDate = eventDate,
            onEventDateChange = { newDate -> eventDate = newDate }
        )


        /* TODO: finish up product button and validation logic */
        Button(
            onClick = {
                if (eventTitle.isNotEmpty()
                    && eventDescription.isNotEmpty()
                    && eventDate.isNotEmpty()
                ) {
                    val newEvent = Event(eventTitle, eventDescription, eventDate, eventVisibility)
                    /* TODO: save new product to db or use a list to hold products (ex: List<Product>) */
                } else {
                    /* TODO: show error message for empty fields */
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .width(200.dp)
                .align(Alignment.CenterHorizontally)
        )
        {
            Text(text = "Add Event")
        }
    }

}


@Composable
fun EventInfoField(eventTitle: String, onEventTitleChange: (String) -> Unit) {
    TextField(
        value = eventTitle,
        onValueChange = onEventTitleChange,
        label = { Text(text = "Event title") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun EventDescriptionField(eventDescription: String, onEventDescriptionChange: (String) -> Unit) {
    TextField(
        value = eventDescription,
        onValueChange = onEventDescriptionChange,
        label = { Text(text = "Event Description") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

/* todo: change the evenDate to a date, currently a string */
@Composable
fun EventDateField(eventDate: String, onEventDateChange: (String) -> Unit) {
    TextField(
        value = eventDate,
        onValueChange = onEventDateChange,
        label = { Text(text = "Event Date") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}
