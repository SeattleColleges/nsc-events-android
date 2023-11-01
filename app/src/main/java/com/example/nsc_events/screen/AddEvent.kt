package com.example.nsc_events.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.nsc_events.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import java.util.Calendar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventPage(navController: NavHostController) {
    var eventTitle by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var eventCategory by  remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var eventLocation by  remember { mutableStateOf("") }
    var eventStartTime by  remember { mutableStateOf("") }
    val scrollState = rememberScrollState()




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
            .verticalScroll(scrollState)
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NSCBanner()
        EventInfoField(
            eventName = eventTitle,
            onEventNameChange = { newEvent -> eventTitle = newEvent }
        )

        EventDescriptionField(
            eventDescription = eventDescription,
            onEventDescriptionChange = { newEventDescription ->
                eventDescription = newEventDescription
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                DatePicker { selectedDate ->
                    eventDate = selectedDate
                }

                Spacer(modifier = Modifier.width(8.dp))  // Space between the DatePicker and TimePicker

                TimePicker { selectedTime ->
                    eventStartTime = selectedTime
                }
            }

            Spacer(modifier = Modifier.height(8.dp))  // Space between the pickers and the feedback text

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Date: $eventDate", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Time: $eventStartTime", style = MaterialTheme.typography.bodySmall)
            }
        }


        // Category field
        GenericTextField(
            value = eventCategory,
            onValueChange = { newCategory -> eventCategory = newCategory },
            label = "Event Category"
        )


        // Location field
        GenericTextField(
            value = eventLocation,
            onValueChange = { newLocation -> eventLocation = newLocation },
            label = "Event Location"
        )


        /* TODO: finish up product button and validation logic */
        Button(
            onClick = {
                if (eventTitle.isNotEmpty()
                    && eventDescription.isNotEmpty()
                    && eventDate.isNotEmpty()
                ) {
                    val newEvent = Event(eventTitle, eventDescription, eventDate)
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
fun EventInfoField(eventName: String, onEventNameChange: (String) -> Unit) {
    TextField(
        value = eventName,
        onValueChange = onEventNameChange,
        label = { Text(text = "Event name") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
}

@Composable
fun EventDescriptionField(eventDescription: String, onEventDescriptionChange: (String) -> Unit) {
    TextField(
        value = eventDescription,
        onValueChange = onEventDescriptionChange,
        label = { Text(text = "Event Description") },
        singleLine = false,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
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
@Composable
fun GenericTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun NSCBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.nsc_logo),
                contentDescription = "NSC Logo",
            )
            Text(
                text = "Add an Event:",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
            )
        }
    }
}

@Composable
fun DatePicker(onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val currentOnDateSelected = rememberUpdatedState(onDateSelected)
    val scope = rememberCoroutineScope()

    Button(onClick = {
        val currentCalendar = Calendar.getInstance()
        scope.launch {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val selectedDate = "$dayOfMonth/${month + 1}/$year"
                    currentOnDateSelected.value(selectedDate)
                },
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }) {
        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar Icon")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Pick a Date")
    }
}

@Composable
fun TimePicker(onTimeSelected: (String) -> Unit) {
    val context = LocalContext.current
    val currentOnTimeSelected = rememberUpdatedState(onTimeSelected)
    val scope = rememberCoroutineScope()

    Button(onClick = {
        val currentCalendar = Calendar.getInstance()
        val currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentCalendar.get(Calendar.MINUTE)

        scope.launch {
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    val selectedTime = String.format("%02d:%02d", hour, minute)
                    currentOnTimeSelected.value(selectedTime)
                },
                currentHour,
                currentMinute,
                true // 24 hour format, set to false for 12 hour format
            ).show()
        }
    }) {
        Icon(imageVector = Icons.Default.Edit, contentDescription = "Clock Icon")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Pick a Time")
    }
}

// This Composable
@Composable
fun DateTimePicker(
    onDateSelected: (String) -> Unit,
    onTimeSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DatePicker(onDateSelected = onDateSelected)
        Spacer(modifier = Modifier.width(16.dp)) // Give some space between the pickers
        TimePicker(onTimeSelected = onTimeSelected)
    }
}