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
import com.example.nsc_events.data.SocialMedia
import java.util.Date


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddEventPage(navController: NavHostController) {
    var eventName by remember { mutableStateOf("") }
    var createdByUser by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var eventCategory by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var eventStartTime by remember { mutableStateOf("") }
    var eventEndTime by remember { mutableStateOf("") }
    var eventLocation by remember { mutableStateOf("") }
    var eventCoverPhoto by remember { mutableStateOf("") }
    var eventHost by remember { mutableStateOf("") }
    var eventWebsite by remember { mutableStateOf("") }
    var eventRegistration by remember { mutableStateOf("") }
    var eventCapacity by remember { mutableStateOf("") }
    var eventCost by remember { mutableStateOf("") }
    var eventTags by remember { mutableStateOf(emptyList<String>()) }
    var eventSchedule by remember { mutableStateOf("") }
    var eventSpeakers by remember { mutableStateOf(emptyList<String>()) }
    var eventPrerequisites by remember { mutableStateOf("") }
    var eventCancellationPolicy by remember { mutableStateOf("") }
    var eventContact by remember { mutableStateOf("") }
    var eventPrivacy by remember { mutableStateOf("") }
    var eventAccessibility by remember { mutableStateOf("") }
    var eventVisibility by remember { mutableStateOf("") }

    /* navigating back to login page */
    TopAppBar(
        title = { Text("Add Event") },
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
            eventName = eventName,
            onEventNameChange = { newEvent: String -> eventName = newEvent }
        )

        EventDescriptionField(
            eventDescription = eventDescription,
            onEventDescriptionChange = { newEventDescription: String ->
                eventDescription = newEventDescription
            }
        )

        EventDateField(
            eventDate = eventDate,
            onEventDateChange = { newDate: String -> eventDate = newDate }
        )


        // Add similar fields for all the other event properties here

        Button(
            onClick = {
                if (eventName.isNotEmpty()
                    && eventDescription.isNotEmpty()
                    && eventDate.isNotEmpty()
                // Check for other properties as well
                ) {
                    val newEvent = Event(
                        createdByUser,
                        eventTitle = eventName,
                        eventDescription = eventDescription,
                        eventCategory,
                        eventDate = Date(),
                        eventStartTime,
                        eventEndTime,
                        eventLocation,
                        eventCoverPhoto,
                        eventHost,
                        eventWebsite,
                        eventRegistration,
                        eventCapacity,
                        eventCost,
                        eventTags,
                        eventSchedule,
                        eventSpeakers ,
                        eventPrerequisites,
                        eventCancellationPolicy,
                        eventContact,
                        eventSocialMedia = SocialMedia(
                            facebook = "Facebook",
                            twitter = "Twitter",
                            instagram = "Instagram",
                            hashtag = "Hashtag"
                        ),
                        eventPrivacy,
                        eventAccessibility ,
                        eventVisibility
                    )
                    /* TODO: save new event to db or use a list to hold events (ex: List<Event>) */
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
fun EventDateField(eventDate: String, onEventDateChange: Any) {

}

@Composable
fun EventDescriptionField(eventDescription: String, onEventDescriptionChange: Any) {

}

@Composable
fun EventInfoField(eventName: String, onEventNameChange: Any) {

}
