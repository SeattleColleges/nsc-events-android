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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.nsc_events.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import java.util.Calendar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventPage(navController: NavHostController) {
    var eventTitle by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var eventCategory by  remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var eventStartTime by remember { mutableStateOf("") }
    var eventEndTime by remember { mutableStateOf("") }
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
                .padding(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                DatePicker { selectedDate ->
                    eventDate = selectedDate
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TimePicker(
                    label = "Start Time",
                    onTimeSelected = { selectedStartTime ->
                        eventStartTime = selectedStartTime
                    },
                    selectedTime = eventStartTime
                )

                Spacer(modifier = Modifier.width(2.dp))

                TimePicker(
                    label = "End Time",
                    onTimeSelected = { selectedEndTime ->
                        eventEndTime = selectedEndTime
                    },
                    selectedTime = eventEndTime
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        /*          Category field          */
        categoryDropDown()


        Spacer(modifier = Modifier.height(200.dp))
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
    OutlinedTextField(
        value = eventName,
        onValueChange = onEventNameChange,
        label = {
            Text(
                text = "Event name",
                style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Medium)
            )
        },
        singleLine = true,
        modifier = Modifier
            .width(400.dp),
        shape = RoundedCornerShape(10.dp),
        textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal),
    )
}

@Composable
fun EventDescriptionField(eventDescription: String, onEventDescriptionChange: (String) -> Unit) {
    OutlinedTextField(
        value = eventDescription,
        onValueChange = onEventDescriptionChange,
        label = {
            Text(
                text = "Event description",
                style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Medium)
            )
        },
        singleLine = true,
        modifier = Modifier
            .width(4000.dp),
        shape = RoundedCornerShape(10.dp),
        textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal),
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
                modifier = Modifier.padding(top = 12.dp, bottom = 6.dp)
            )
        }
    }
}

@Composable
fun DatePicker(onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val currentOnDateSelected = rememberUpdatedState(onDateSelected)
    val scope = rememberCoroutineScope()

    // Access the global variable
    var eventDate by remember { mutableStateOf("") }

    Button(onClick = {
        val currentCalendar = Calendar.getInstance()
        scope.launch {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val date = "$dayOfMonth/${month + 1}/$year"
                    eventDate = date  // Update the global variable
                    currentOnDateSelected.value(date)
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

        if (eventDate.isNotEmpty()) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.Green)
        }
    }
}


@Composable
fun TimePicker(label: String, onTimeSelected: (String) -> Unit, selectedTime: String) {
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
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)

        // Display the checkmark if the time has been selected
        if (selectedTime.isNotEmpty()) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.Green)
        } else {
            Icon(imageVector = Icons.Rounded.AddCircle, contentDescription = "Add Icon")
        }
    }
}

@Composable
fun categoryDropDown() {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            OutlinedButton(
                onClick = { expanded = true },
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(text = "Choose a Category")
                Spacer(modifier = Modifier.width(4.dp))  // Add some spacing between text and icon
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Open dropdown menu")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(2.dp, 10.dp)
            ) {
                DropdownMenuItem(
                    text = { Text("Category1") },
                    onClick = { /* Handle Stuff1! */ },
                )
                DropdownMenuItem(
                    text = { Text("Category2") },
                    onClick = { /* Handle Stuff1! */ },
                )
                DropdownMenuItem(
                    text = { Text("Category3") },
                    onClick = { /* Handle Stuff1! */ },
                )
                DropdownMenuItem(
                    text = { Text("Category4") },
                    onClick = { /* Handle Stuff1! */ },
                )
                DropdownMenuItem(
                    text = { Text("Category5") },
                    onClick = { /* Handle Stuff1! */ },
                )
            }
        }
    }
}
