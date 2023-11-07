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
import android.net.Uri
import androidx.compose.ui.graphics.PathEffect
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import java.util.Calendar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventPage(navController: NavHostController) {

    /* variables and control logic for eventTitle */
    var eventTitle by remember { mutableStateOf("") }
    var eventTitleError by remember { mutableStateOf(false) }
    val onEventNameChange = { newEvent: String ->
        eventTitleError = newEvent.isEmpty()
        eventTitle = newEvent
    }


    /* variables and control logic for eventTitle */
    var eventDescription by remember { mutableStateOf("") }
    var eventDescriptionError by remember { mutableStateOf(false)}
    val onEventDescriptionChange = { newEvent: String ->
        eventDescriptionError = newEvent.isEmpty()
        eventDescription = newEvent
    }

    /* variables and control logic for dates and times */
    var eventDate by remember { mutableStateOf("") }
    var isDateError by remember { mutableStateOf(false) }

    var eventStartTime by remember { mutableStateOf("") }
    var isStartTimeError by remember { mutableStateOf(false) }
    var eventEndTime by remember { mutableStateOf("") }
    var isEndTimeError by remember { mutableStateOf(false) }
    val validateStartTime = {
        isStartTimeError = eventStartTime.isBlank()
    }
    val validateEndTime = {
        isEndTimeError = eventEndTime.isBlank()
    }

    var eventCategory by  remember { mutableStateOf("") }
    var eventLocation by  remember { mutableStateOf("") }
    var eventImage by  remember { mutableStateOf("") }


    val scrollState = rememberScrollState()
    var showAlert by remember { mutableStateOf(false) }

    /* Assembled event object */
    var newEvent by remember { mutableStateOf<Event?>(null) }





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

    /* Center Column */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Banner with text
        NSCBanner()

        // Event Name with error
        EventInfoField(
            eventName = eventTitle,
            onEventNameChange = onEventNameChange,
            isError = eventTitleError
        )

        // Event Description with error
        EventDescriptionField(
            eventDescription = eventDescription,
            onEventDescriptionChange = onEventDescriptionChange,
            isError = eventDescriptionError
        )

        /* Date and time pickers */
        /* Date and time pickers */
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                DatePicker(
                    onDateSelected = { newDate ->
                        eventDate = newDate
                        isDateError = false  // Reset error state when a date is picked
                    },
                    isError = isDateError
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // TimePicker for the start time
                Column(modifier = Modifier.wrapContentWidth()) {
                    TimePicker(
                        label = "Start Time",
                        selectedTime = eventStartTime,
                        isError = isStartTimeError,
                        onTimeSelected = { time ->
                            eventStartTime = time
                            isStartTimeError = time.isEmpty()
                        }
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // TimePicker for the end time
                Column(modifier = Modifier.wrapContentWidth()) {
                    TimePicker(
                        label = "End Time",
                        selectedTime = eventEndTime,
                        isError = isEndTimeError,
                        onTimeSelected = { time ->
                            eventEndTime = time
                            isEndTimeError = time.isEmpty()
                        }
                    )
                }
            }
        }

        /*          Category field          */
        CategoryDropDown()

        /*          Location field          */
        LocationInputField(onLocationChange = { newLocation ->
            eventLocation = newLocation
        })

        /*          Image upload button     */
        ImageUploadButton(onImagePicked = { uri -> /* TODO: Do something with this */ })

        Spacer(modifier = Modifier.height(20.dp))
        /* TODO: finish up product button and validation logic */
        Button(

            onClick = {
                // Double Checking values via the alert
                showAlert = true
                eventTitleError = eventTitle.isEmpty()
                eventDescriptionError = eventDescription.isEmpty()
                isDateError = eventDate.isEmpty()
                isStartTimeError = eventStartTime.isEmpty()
                isEndTimeError = eventEndTime.isEmpty()
                if (!eventTitleError && !eventDescriptionError && !isDateError && !isStartTimeError && !isEndTimeError) {
                    newEvent =
                        Event(eventTitle, eventDescription, eventDate, eventStartTime, eventEndTime)
                    showAlert = true
                /* TODO: save new product to db or use a list to hold products (ex: List<Product>) */
                } else {
                    /* TODO: show error message for empty fields */
                }


            },
            modifier = Modifier
                .padding(16.dp)
                .width(240.dp)
        )
        {
            Text(text = "Add Event")
        }
        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                title = {
                    Text(text = "Event Details")
                },
                text = {
                    Column {
                        Text("Title: ${newEvent?.eventName}")
                        Text("Description: ${newEvent?.description}")
                        Text("Date: ${newEvent?.date}")
                        Text("Start Time: ${newEvent?.startTime}")
                        Text("End Time: ${newEvent?.endTime}")
                        // Text("Category: ${event.category}")
                    }
                },
                confirmButton = {
                    Button(onClick = { showAlert = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}


/* Begin composables */
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
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top = 12.dp, bottom = 6.dp)
            )
        }
    }
}

@Composable
fun EventInfoField(
    eventName: String,
    onEventNameChange: (String) -> Unit,
    isError: Boolean
) {
        OutlinedTextField(
            value = eventName,
            onValueChange = onEventNameChange,
            label = {
                Text("Event name *")
            },
            singleLine = true,
            modifier = Modifier.width(400.dp),
            isError = isError,
            trailingIcon = {
                if (eventName.isNotEmpty() && !isError) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.Green)
                }
            }
        )
        if (isError) {
            Text(
                text = "Event name is required.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }
}
@Composable
fun EventDescriptionField(
    eventDescription: String,
    onEventDescriptionChange: (String) -> Unit,
    isError: Boolean
) {
    Column {
        OutlinedTextField(
            value = eventDescription,
            onValueChange = onEventDescriptionChange,
            label = {
                Text("Event description *")
            },
            singleLine = false,
            modifier = Modifier.width(400.dp),
            isError = isError,
            maxLines = 4,
            trailingIcon = {
                if (eventDescription.isNotEmpty() && !isError) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.Green)
                }
            }
        )
        if (isError) {
            Text(
                text = "Event description is required.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}


@Composable
fun DatePicker(
    onDateSelected: (String) -> Unit,
    isError: Boolean
    ) {
    val context = LocalContext.current
    val currentOnDateSelected = rememberUpdatedState(onDateSelected)
    val scope = rememberCoroutineScope()
    var eventDate by remember { mutableStateOf("") }

    Column {
        Button(onClick = {
            val currentCalendar = Calendar.getInstance()
            scope.launch {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val date = "$dayOfMonth/${month + 1}/$year"
                        eventDate = date
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
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.Green
                )
            }
        }
        if (isError) {
            Text(
                text = "A date must be selected",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp, top = 2.dp)
            )
        }
    }
}


@Composable
fun TimePicker(
    label: String,
    selectedTime: String,
    isError: Boolean,
    onTimeSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val currentOnTimeSelected = rememberUpdatedState(onTimeSelected)
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.wrapContentSize()) {
        Button(
            onClick = {
                val timePickerDialog = TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        val time = String.format("%02d:%02d", hourOfDay, minute)
                        currentOnTimeSelected.value(time)
                    },
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE),
                    true // or false if you want 12 hour time
                )
                timePickerDialog.show()
            },
        ) {
            Text(text = selectedTime.ifEmpty { label })
            if (selectedTime.isNotEmpty() && !isError) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Selected", tint = Color.Green)
            }
        }
        if (isError) {
            Text(
                text = "Time is required",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

    @Composable
fun CategoryDropDown() {
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
                Spacer(modifier = Modifier.width(4.dp))
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

@Composable
fun LocationInputField(onLocationChange: (String) -> Unit) {
    var location by remember { mutableStateOf("") }

    OutlinedTextField(
        value = location,
        onValueChange = { newValue ->
            location = newValue
            onLocationChange(newValue)
        },
        leadingIcon = {
            Icon(
                Icons.Filled.LocationOn,
                contentDescription = "Location Icon",
                tint = Color.Gray
            )
        },
        label = {
            Text(
                text = "Location",
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
fun ImageUploadButton(onImagePicked: (Uri?) -> Unit) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        onImagePicked(imageUri)
    }

    val boxSize = Modifier
        .fillMaxWidth()
        .padding(26.dp)
        .height(200.dp)

    Box(
        modifier = boxSize
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .clickable {
                launcher.launch("image/*")
            }
            .clip(RoundedCornerShape(4.dp))
            .drawDottedBorder(2.dp, Color.DarkGray, 12.dp),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri == null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(12.dp))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Upload Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Upload Event Image", fontSize = 16.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(12.dp))
            }
        } else {
            imageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(model = uri),
                    contentDescription = "Selected Image",
                    modifier = boxSize,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
@Composable
fun Modifier.drawDottedBorder(
    strokeWidth: Dp,
    color: Color,
    dashWidth: Dp
): Modifier = composed {
    this.then(
        drawWithContent {
            drawContent()
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth.toPx(), dashWidth.toPx()), 0f)
            val halfStrokeWidth = strokeWidth.toPx() / 3
            translate(top = halfStrokeWidth, left = halfStrokeWidth) {
                drawRoundRect(
                    color = color,
                    size = size.copy(width = size.width - strokeWidth.toPx(), height = size.height - strokeWidth.toPx()),
                    style = Stroke(width = strokeWidth.toPx(), pathEffect = pathEffect as PathEffect?)
                )
            }
        }
    )
}
