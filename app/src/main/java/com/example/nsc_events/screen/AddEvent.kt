package com.example.nsc_events.screen

import android.app.DatePickerDialog
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
import com.example.nsc_events.model.Event
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.nsc_events.R
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Paint.Style
import android.media.MediaSyncEvent.createEvent
import android.net.Uri
import android.net.http.HttpException
import android.widget.Toast
import androidx.compose.ui.graphics.PathEffect
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.nsc_events.data.network.dto.event_dto.EventRequest
import com.example.nsc_events.data.network.dto.event_dto.EventResponse
import com.example.nsc_events.data.network.service.EventService
import com.example.nsc_events.model.SocialMedia
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


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
    var eventRegistration by remember { mutableStateOf("") }
    var eventCapacity by remember { mutableStateOf("") }
    var eventSchedule by remember { mutableStateOf("") }
    var eventSpeakers by remember { mutableStateOf(arrayOf<String>()) }
    var eventPrerequisites by remember { mutableStateOf("") }
    var eventCancellationPolicy by remember { mutableStateOf("") }
    var eventContact by remember { mutableStateOf("") }
    var eventSocialMedia by remember { mutableStateOf(SocialMedia("", "", "", "")) }
    var eventPrivacy by remember { mutableStateOf("") }
    var eventAccessibility by remember { mutableStateOf("") }
    var eventNote by remember { mutableStateOf("") }
    var isHidden by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()
    val current = LocalContext.current

    var selectedCategory by remember { mutableStateOf<String?>(null) }

    /* updating eventTags to be a button options from a text field */
    val tagsList = listOf("Professional Development", "Club", "Social", "Tech", "Cultural", "Study", "Coffee", "Networking")
    var eventTags by remember { mutableStateOf( mutableListOf<String>() ) }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
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
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
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
                CategoryDropDown(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category ->
                        selectedCategory = category
                    }
                )
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
                EventTagsSelectionField(
                    tagsList = tagsList,
                    eventTags = eventTags,
                    onTagsChange = { updatedTags ->
                        eventTags.clear()
                        eventTags.addAll(updatedTags)
                    }
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

            item {
                EventNoteField(
                    eventNote = eventNote,
                    onEventNoteChange = { newNote ->
                        eventNote = newNote
                    }
                )
            }

            item {
                Button(
                    onClick = {
                        newEvent =
                            Event(
                                eventTitle,
                                eventDescription,
                                eventCategory,
                                eventDate!!,
                                eventStartTime,
                                eventEndTime,
                                eventLocation,
                                eventCoverPhoto,
                                eventHost,
                                eventRegistration,
                                eventCapacity,
                                eventTags,
                                eventSchedule,
                                eventSpeakers,
                                eventPrerequisites,
                                eventCancellationPolicy,
                                eventContact,
                                eventSocialMedia,
                                eventPrivacy,
                                eventAccessibility,
                                eventNote,
                                isHidden
                            )

                        eventTitleError = eventTitle.isEmpty()
                        eventDescriptionError = eventDescription.isEmpty()
                        isDateError = eventDate == null
                        isStartTimeError = eventStartTime.isEmpty()
                        isEndTimeError = eventEndTime.isEmpty()

                        coroutineScope.launch {
                            createEvent(newEvent!!, navController, current)
                        }
                        /* TODO: save new product to db or use a list to hold products (ex: List<Product>) */
                    },
                    modifier = Modifier
                        .padding(4.dp)
                        .width(240.dp)
                )
                {
                    Text(text = "Add Event")
                }
            }

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
                        val calendar = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, hourOfDay)
                            set(Calendar.MINUTE, minute)
                        }
                        val formattedTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
                        currentOnTimeSelected.value(formattedTime)
                    },
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE),
                    false // or false if you want 12 hour time
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
fun CategoryDropDown(selectedCategory: String?, onCategorySelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var displayText by remember { mutableStateOf("Choose a Category")}

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
                Text(text = selectedCategory ?: displayText)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Open dropdown menu")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(2.dp, 10.dp)
            ) {
                // List of categories
                val categories = listOf("Category1", "Category2", "Category3", "Category4", "Category5")
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            onCategorySelected(category)
                            expanded = false
                        },
                    )
                }
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

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EventTagsSelectionField(tagsList: List<String>, eventTags: MutableList<String>,
                            onTagsChange: (List<String>) -> Unit) {
    val tagState = remember { mutableStateListOf<Pair<String, Boolean>>().apply {
        tagsList.forEach { add(it to eventTags.contains(it)) }
    }}

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Event Tags", style = MaterialTheme.typography.bodyLarge)
        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            tagState.forEachIndexed{ index, tag ->
                FilterChip(
                    selected = tag.second,
                    onClick = {
                        val newTagState = tagState[index].copy(second = !tag.second)
                        tagState[index] = newTagState
                        onTagsChange(tagState.filter { it.second }.map { it.first })
                    },
                    label = { Text(tag.first) }
                )
            }
        }
    }
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

@Composable
fun EventNoteField(
    eventNote: String,
    onEventNoteChange: (String) -> Unit
) {
    TextField(
        value = eventNote,
        onValueChange = onEventNoteChange,
        label = { Text(text = "Event Note") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}


suspend fun createEvent(
    newEvent: Event,
    navController: NavHostController,
    current: Context
) {

    try {
        // constructing event request object from state vars
        val eventRequest = EventRequest(
            eventTitle = newEvent.eventTitle,
            eventDescription = newEvent.eventDescription,
            eventCategory = newEvent.eventCategory,
            eventDate = newEvent.eventDate,
            eventStartTime = newEvent.eventStartTime,
            eventEndTime = newEvent.eventEndTime,
            eventLocation = newEvent.eventLocation,
            eventCoverPhoto = newEvent.eventCoverPhoto,
            eventHost = newEvent.eventHost,
            eventRegistration = newEvent.eventRegistration,
            eventCapacity = newEvent.eventCapacity,
            eventTags = newEvent.eventTags,
            eventSchedule = newEvent.eventSchedule,
            eventSpeakers = newEvent.eventSpeakers,
            eventPrerequisites = newEvent.eventPrerequisites,
            eventCancellationPolicy = newEvent.eventCancellationPolicy,
            eventContact = newEvent.eventContact,
            eventSocialMedia = newEvent.eventSocialMedia,
            eventPrivacy = newEvent.eventPrivacy,
            eventAccessibility = newEvent.eventAccessibility,
            eventNote = newEvent.eventNote,
            isHidden = newEvent.isHidden
        )

        val eventResponse = EventService.create().createNewEvent(eventRequest)
        if (eventResponse != null && eventResponse.message == "Activity created successfully.") {
            // todo: route to proper page (home page) after successfully creating an event
            navController.navigate(Routes.HomePage.route)
            Toast.makeText(current, "Event created successfully!", Toast.LENGTH_SHORT).show()
            // create and show toast message displaying creation of event
        } else {
            // todo: validation needed on fields that are empty or not properly entered
            Toast.makeText(current, "Failed to create event.", Toast.LENGTH_SHORT).show()
        }
    } catch (e: HttpException) {
        e.printStackTrace()
        Toast.makeText(current, "Error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
    }

}

