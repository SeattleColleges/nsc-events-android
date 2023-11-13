package com.example.nsc_events.screen

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.Box

import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nsc_events.Routes
import com.example.nsc_events.data.Event
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
// import androidx.compose.material.icons.rounded.AddCircle
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
import com.example.nsc_events.data.SocialMedia
import java.util.Calendar
import java.util.Date


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
    var eventDescriptionError by remember { mutableStateOf(false) }
    val onEventDescriptionChange = { newEvent: String ->
        eventDescriptionError = newEvent.isEmpty()
        eventDescription = newEvent
    }

    /* variables and control logic for dates and times */
    var eventDate by remember { mutableStateOf<Date?>(null) }
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

    var eventLocation by remember { mutableStateOf("") }
    var eventImage by remember { mutableStateOf("") }

    var showAlert by remember { mutableStateOf(false) }

    /* Assembled event object */
    var newEvent by remember { mutableStateOf<Event?>(null) }

    var eventCategory by remember { mutableStateOf("") }
    var eventCoverPhoto by remember { mutableStateOf("") }
    var eventHost by remember { mutableStateOf("") }
    var eventWebsite by remember { mutableStateOf("") }
    var eventRegistration by remember { mutableStateOf("") }
    var eventCapacity by remember { mutableStateOf("") }
    var eventCost by remember { mutableStateOf("") }
    var eventTags by remember { mutableStateOf(arrayOf<String>()) }
    var eventSchedule by remember { mutableStateOf("") }
    var eventSpeakers by remember { mutableStateOf(arrayOf<String>()) }
    var eventPrerequisites by remember { mutableStateOf("") }
    var eventCancellationPolicy by remember { mutableStateOf("") }
    var eventContact by remember { mutableStateOf("") }
    var eventSocialMedia by remember { mutableStateOf(SocialMedia("", "", "", "")) }
    var eventPrivacy by remember { mutableStateOf("") }
    var eventAccessibility by remember { mutableStateOf("") }
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

    /* Center Column */
    LazyColumn(
        modifier = Modifier
            .padding(
                top = 72.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        //avoid overlapping top app bar
    ) {

        // Banner with text
        item {
            NSCBanner()
        }

        item {
            // Event Name with error
            EventInfoField(
                eventName = eventTitle,
                onEventNameChange = onEventNameChange,
                isError = eventTitleError
            )
        }

        item {
            // Event Description with error
            EventDescriptionField(
                eventDescription = eventDescription,
                onEventDescriptionChange = onEventDescriptionChange,
                isError = eventDescriptionError
            )
        }

        item {
        /* Date and time pickers */
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                DatePicker(
                    onDateSelected = { newDate ->
                        eventDate = newDate
                        isDateError = false  // Reset error state when a date is picked
                    },
                    isError = isDateError
                )
            }
        }

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

                Spacer(modifier = Modifier.width(4.dp))

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

        item {
            /*          Category field          */
            CategoryDropDown()
        }

        item {
            /*          Location field          */
            LocationInputField(onLocationChange = { newLocation ->
                eventLocation = newLocation
            })
        }

        item {
            /*          Image upload button     */
            ImageUploadButton(onImagePicked = { uri -> /* TODO: Do something with this */ })

            /* TODO: finish up product button and validation logic */
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(

                    onClick = {
                        eventTitleError = eventTitle.isEmpty()
                        eventDescriptionError = eventDescription.isEmpty()
                        isDateError = eventDate == null
                        isStartTimeError = eventStartTime.isEmpty()
                        isEndTimeError = eventEndTime.isEmpty()
                        val isInputValid = eventTitle.isNotEmpty() && eventDescription.isNotEmpty() && eventCategory.isNotEmpty() && eventDate != null && eventStartTime.isNotEmpty() && eventEndTime.isNotEmpty()
                            newEvent = eventDate?.let {
                                Event(
                                    eventTitle,
                                    eventDescription,
                                    eventCategory,
                                    it,
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
                                    eventSpeakers,
                                    eventPrerequisites,
                                    eventCancellationPolicy,
                                    eventContact,
                                    eventSocialMedia,
                                    eventPrivacy,
                                    eventAccessibility,
                                    eventVisibility
                                )
                            }
                        showAlert = true
                            /* TODO: save new product to db or use a list to hold products (ex: List<Product>) */
                    },
                    modifier = Modifier
                        .padding(4.dp)
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
                                Text("Title: ${newEvent?.eventTitle}")
                                Text("Description: ${newEvent?.eventDescription}")
                                Text("Date: ${newEvent?.date}")
                                Text("Start Time: ${newEvent?.eventStartTime}")
                                Text("End Time: ${newEvent?.eventEndTime}")
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

            item {
                EventLocationField(
                    eventLocation = eventLocation,
                    onEventLocationChange = { newLocation -> eventLocation = newLocation }
                )
            }

            item {
                EventCoverPhotoField(
                    eventCoverPhoto = eventCoverPhoto,
                    onEventCoverPhotoChange = { newCoverPhoto -> eventCoverPhoto = newCoverPhoto }
                )
            }

            item {
                EventHostField(
                    eventHost = eventHost,
                    onEventHostChange = { newHost -> eventHost = newHost }
                )
            }

            item {
                EventWebsiteField(
                    eventWebsite = eventWebsite,
                    onEventWebsiteChange = { newWebsite -> eventWebsite = newWebsite }
                )
            }

            item {
                EventRegistrationField(
                    eventRegistration = eventRegistration,
                    onEventRegistrationChange = { newRegistration ->
                        eventRegistration = newRegistration
                    }
                )
            }

            item {
                EventCapacityField(
                    eventCapacity = eventCapacity,
                    onEventCapacityChange = { newCapacity -> eventCapacity = newCapacity }
                )
            }

            item {
                EventCostField(
                    eventCost = eventCost,
                    onEventCostChange = { newCost -> eventCost = newCost }
                )
            }

            item {
                TagsField(
                    eventTags = eventTags,
                    onEventTagsChange = { newTags -> eventTags = newTags }
                )
            }

            item {
                EventScheduleField(
                    eventSchedule = eventSchedule,
                    onEventScheduleChange = { newSchedule -> eventSchedule = newSchedule }
                )
            }

            item {
                SpeakersField(
                    eventSpeakers = eventSpeakers,
                    onEventSpeakersChange = { newSpeakers -> eventSpeakers = newSpeakers }
                )
            }

            item {
                EventPrerequisitesField(
                    eventPrerequisites = eventPrerequisites,
                    onEventPrerequisitesChange = { newPrerequisites ->
                        eventPrerequisites = newPrerequisites
                    }
                )
            }

            item {
                EventCancellationPolicyField(
                    eventCancellationPolicy = eventCancellationPolicy,
                    onEventCancellationPolicyChange = { newCancellationPolicy ->
                        eventCancellationPolicy = newCancellationPolicy
                    }
                )
            }

            item {
                EventContactField(
                    eventContact = eventContact,
                    onEventContactChange = { newContact -> eventContact = newContact }
                )
            }

            item {
                SocialMediaField(
                    socialMedia = eventSocialMedia,
                    onSocialMediaChange = { newSocialMedia -> eventSocialMedia = newSocialMedia }
                )
            }

            item {
                EventPrivacyField(
                    eventPrivacy = eventPrivacy,
                    onEventPrivacyChange = { newPrivacy -> eventPrivacy = newPrivacy }
                )
            }

            item {
                EventAccessibilityField(
                    eventAccessibility = eventAccessibility,
                    onEventAccessibilityChange = { newAccessibility ->
                        eventAccessibility = newAccessibility
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
                    modifier = Modifier.padding(top = 2.dp, bottom = 6.dp)
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
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.Green
                    )
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
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.Green
                        )
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
        onDateSelected: (Date) -> Unit,
        isError: Boolean
    ) {
        val context = LocalContext.current
        val currentOnDateSelected = rememberUpdatedState(onDateSelected)
        val scope = rememberCoroutineScope()
        var eventDate by remember { mutableStateOf<Date?>(null) }

        Column {
            Button(onClick = {
                val currentCalendar = Calendar.getInstance()
                scope.launch {
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val calendar = Calendar.getInstance()
                            calendar.set(year, month, dayOfMonth)
                            val selectedDate = calendar.time
                            eventDate = selectedDate
                            currentOnDateSelected.value(selectedDate)
                        },
                        currentCalendar.get(Calendar.YEAR),
                        currentCalendar.get(Calendar.MONTH),
                        currentCalendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }

            }) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar Icon")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Pick a Date")
                if (eventDate !== null) {
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
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.Green
                    )
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
            .padding(12.dp)
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
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Upload Icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Upload Event Image", fontSize = 16.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(4.dp))
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
                val pathEffect =
                    PathEffect.dashPathEffect(floatArrayOf(dashWidth.toPx(), dashWidth.toPx()), 0f)
                val halfStrokeWidth = strokeWidth.toPx() / 3
                translate(top = halfStrokeWidth, left = halfStrokeWidth) {
                    drawRoundRect(
                        color = color,
                        size = size.copy(
                            width = size.width - strokeWidth.toPx(),
                            height = size.height - strokeWidth.toPx()
                        ),
                        style = Stroke(
                            width = strokeWidth.toPx(),
                            pathEffect = pathEffect as PathEffect?
                        )
                    )
                }
            }
        )
    }

    @Composable
    fun EventLocationField(eventLocation: String, onEventLocationChange: (String) -> Unit) {
        TextField(
            value = eventLocation,
            onValueChange = onEventLocationChange,
            label = { Text(text = "Event location") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun EventCoverPhotoField(eventCoverPhoto: String, onEventCoverPhotoChange: (String) -> Unit) {
        TextField(
            value = eventCoverPhoto,
            onValueChange = onEventCoverPhotoChange,
            label = { Text(text = "Event cover photo") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun EventHostField(eventHost: String, onEventHostChange: (String) -> Unit) {
        TextField(
            value = eventHost,
            onValueChange = onEventHostChange,
            label = { Text(text = "Event Host") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun EventWebsiteField(eventWebsite: String, onEventWebsiteChange: (String) -> Unit) {
        TextField(
            value = eventWebsite,
            onValueChange = onEventWebsiteChange,
            label = { Text(text = "Event Website") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun EventRegistrationField(
        eventRegistration: String,
        onEventRegistrationChange: (String) -> Unit
    ) {
        TextField(
            value = eventRegistration,
            onValueChange = onEventRegistrationChange,
            label = { Text(text = "Event Registration") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun EventCapacityField(eventCapacity: String, onEventCapacityChange: (String) -> Unit) {
        TextField(
            value = eventCapacity,
            onValueChange = onEventCapacityChange,
            label = { Text(text = "Event Capacity") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun EventCostField(eventCost: String, onEventCostChange: (String) -> Unit) {
        TextField(
            value = eventCost,
            onValueChange = onEventCostChange,
            label = { Text(text = "Event Cost") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun TagsField(eventTags: Array<String>, onEventTagsChange: (Array<String>) -> Unit) {
        TextField(
            value = eventTags.joinToString(", "),
            onValueChange = {
                val tags = it.split(",").map { it.trim() }.toTypedArray()
                onEventTagsChange(tags)
            },
            label = { Text(text = "Event Tags") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }


    @Composable
    fun EventScheduleField(eventSchedule: String, onEventScheduleChange: (String) -> Unit) {
        TextField(
            value = eventSchedule,
            onValueChange = onEventScheduleChange,
            label = { Text(text = "Event Schedule") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun SpeakersField(
        eventSpeakers: Array<String>,
        onEventSpeakersChange: (Array<String>) -> Unit
    ) {
        TextField(
            value = eventSpeakers.joinToString(", "),
            onValueChange = {
                val speakers = it.split(",").map { it.trim() }.toTypedArray()
                onEventSpeakersChange(speakers)
            },
            label = { Text(text = "Event Speakers") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }


    @Composable
    fun EventPrerequisitesField(
        eventPrerequisites: String,
        onEventPrerequisitesChange: (String) -> Unit
    ) {
        TextField(
            value = eventPrerequisites,
            onValueChange = onEventPrerequisitesChange,
            label = { Text(text = "Event Prerequisites") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun EventCancellationPolicyField(
        eventCancellationPolicy: String,
        onEventCancellationPolicyChange: (String) -> Unit
    ) {
        TextField(
            value = eventCancellationPolicy,
            onValueChange = onEventCancellationPolicyChange,
            label = { Text(text = "Event Cancellation Policy") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun EventContactField(eventContact: String, onEventContactChange: (String) -> Unit) {
        TextField(
            value = eventContact,
            onValueChange = onEventContactChange,
            label = { Text(text = "Event Contact") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun SocialMediaField(socialMedia: SocialMedia, onSocialMediaChange: (SocialMedia) -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = socialMedia.facebook,
                onValueChange = { onSocialMediaChange(socialMedia.copy(facebook = it)) },
                label = { Text(text = "Facebook") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            TextField(
                value = socialMedia.twitter,
                onValueChange = { onSocialMediaChange(socialMedia.copy(twitter = it)) },
                label = { Text(text = "Twitter") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            TextField(
                value = socialMedia.instagram,
                onValueChange = { onSocialMediaChange(socialMedia.copy(instagram = it)) },
                label = { Text(text = "Instagram") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            TextField(
                value = socialMedia.hashtag,
                onValueChange = { onSocialMediaChange(socialMedia.copy(hashtag = it)) },
                label = { Text(text = "Hashtag") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }

    @Composable
    fun EventPrivacyField(eventPrivacy: String, onEventPrivacyChange: (String) -> Unit) {
        TextField(
            value = eventPrivacy,
            onValueChange = onEventPrivacyChange,
            label = { Text(text = "Event Privacy") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
    @Composable
    fun EventAccessibilityField(
        eventAccessibility: String,
        onEventAccessibilityChange: (String) -> Unit
    ) {
        TextField(
            value = eventAccessibility,
            onValueChange = onEventAccessibilityChange,
            label = { Text(text = "Event Accessibility") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }