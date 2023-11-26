package com.example.nsc_events.screen

import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.nsc_events.R
import com.example.nsc_events.Routes
import com.example.nsc_events.data.Datasource
import com.example.nsc_events.model.Event
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.*
import android.app.DatePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog

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
                            // This goes back to the event it came from
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
    val eventStartTimeState = remember { mutableStateOf(event.eventStartTime) }
    val eventEndTimeState = remember { mutableStateOf(event.eventEndTime) }
    val eventLocationState = remember { mutableStateOf(event.eventLocation) }
    var datePickerDialogShown by remember { mutableStateOf(false) }
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    var isSave by remember { mutableStateOf(false) }

    if (datePickerDialogShown) {
        val calendar = Calendar.getInstance()
        MaterialDatePickerDialog(
            initialDate = calendar.time,
            onDateSelected = { date ->
                selectedDate = date
                datePickerDialogShown = false
            },
            onDismissRequest = {
                datePickerDialogShown = false
            }
        )
    }


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
            Button(onClick = { datePickerDialogShown = true }) {
                Text(text = "Select Date")
            }
            selectedDate?.let {
                Text("Selected Date: ${dateFormatter.format(it)}")
            }
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
                isSave = !isSave
            }) {
                Text(text = "Save  ")
                Icon(
                    imageVector = Icons.Default.Check, contentDescription = "Save"
                )

            }
            if (isSave) {
                ConfirmSaveDialogIndividual(onConfirm = {
                    // TODO: Save the event to the database
                    // Update the event with the new data
                    isSave = false
                }, onDismiss = {
                    isSave = false
                })
            }
        }
    }
}
@Composable
fun MaterialDatePickerDialog(
    initialDate: Date,
    onDateSelected: (Date) -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply { time = initialDate }

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            onDateSelected(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        setOnDismissListener { onDismissRequest() }
        show()
    }
}

@Composable
fun ConfirmSaveDialogIndividual(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .border(1.dp, Color.White),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Are you sure?")
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { onDismiss() }, modifier = Modifier.padding(8.dp)
                    ) {
                        Text("No")
                    }
                    Button(
                        onClick = { onConfirm() }, modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Yes")
                    }
                }
            }
        }
    }
}