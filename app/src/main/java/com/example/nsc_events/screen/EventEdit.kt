package com.example.nsc_events.screen

import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nsc_events.R
import com.example.nsc_events.Routes
import com.example.nsc_events.data.Datasource
import com.example.nsc_events.model.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventEditPage(navController: NavController, eventId: String) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            /* Back arrow that navigates back to login page */
            TopAppBar(
                title = { Text("Back to Event") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate("${Routes.EventDetail.route}/$eventId")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Event"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(text = eventId)
            val event = Datasource().loadEvents().find { it.eventTitle == eventId }!!
            EventEditCard(event = event, navController = navController)
        }
    }
}

@Composable
fun EventEditCard(event: Event, navController: NavController) {
    val eventTitleState = remember { mutableStateOf(event.eventTitle) }
    val eventDescriptionState = remember { mutableStateOf(event.eventDescription) }
    // val eventDateState = remember { mutableStateOf(event.eventDate) }
    val eventStartTimeState = remember { mutableStateOf(event.eventStartTime) }
    val eventEndTimeState = remember { mutableStateOf(event.eventEndTime) }
    val eventLocationState = remember { mutableStateOf(event.eventLocation) }

    Card(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = event.eventCoverPhoto.toInt()),
                contentDescription = stringResource(id = R.string.event_cover_photo_description),
                modifier = Modifier
                    .size(200.dp)
                    .padding(2.dp),
            )
            OutlinedTextField(
                value = eventTitleState.value,
                onValueChange = { eventTitleState.value = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = eventDescriptionState.value,
                onValueChange = { eventDescriptionState.value = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = eventStartTimeState.value,
                onValueChange = { eventStartTimeState.value = it },
                label = { Text("Start Time") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = eventEndTimeState.value,
                onValueChange = { eventEndTimeState.value = it },
                label = { Text("End Time") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = eventLocationState.value,
                onValueChange = { eventLocationState.value = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                // TODO: Submit the data
            }) {
                Text("Submit")
            }
        }
    }
}