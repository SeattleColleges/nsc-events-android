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
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nsc_events.Routes
import com.example.nsc_events.data.Event
import com.example.nsc_events.data.SocialMedia
import java.util.Calendar
import java.util.Date


/*TODO: Fix overlapping fields*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventPage(navController: NavHostController) {
    var eventTitle by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var eventCategory by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf(Calendar.getInstance().time) }
    var eventStartDate by remember { mutableStateOf("") }
    var eventEndDate by remember { mutableStateOf("") }
    var eventLocation by remember { mutableStateOf("") }
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 72.dp, start = 16.dp, end = 16.dp, bottom = 16.dp) //avoid overlapping top app bar
        ) {
            item {
                EventInfoField(
                    eventTitle = eventTitle,
                    onEventTitleChange = { newTitle -> eventTitle = newTitle }
                )
            }


            item {
                EventDescriptionField(
                    eventDescription = eventDescription,
                    onEventDescriptionChange = { newEventDescription ->
                        eventDescription = newEventDescription
                    }
                )
            }

            item {
                EventCategoryField(
                    eventCategory = eventCategory,
                    onEventCategoryChange = { newCategory -> eventCategory = newCategory }
                )

            }

            item {
                EventDateField(
                    eventDate = eventDate,
                    onEventDateChange = { newDate -> eventDate = newDate }
                )
            }

            item {
                EventStartDateField(
                    eventStartDate = eventStartDate,
                    onEventStartDateChange = { newDate -> eventStartDate = newDate }
                )
            }

            item {
                EventEndDateField(
                    eventEndDate = eventEndDate,
                    onEventEndDateChange = { newDate -> eventEndDate = newDate }
                )
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


            /* TODO: finish up product button and validation logic */
            item {
                Button(
                    onClick = {
                        if (eventTitle.isNotEmpty()
                            && eventDescription.isNotEmpty()
                            && eventCategory.isNotEmpty()
                        ) {
                            val newEvent = Event(
                                eventTitle,
                                eventDescription,
                                eventCategory,
                                eventDate,
                                eventStartDate,
                                eventEndDate,
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
                            /* TODO: save new event to db or use a list to hold events (ex: List<Event>) */
                        } else {
                            /* TODO: show error message for empty fields */
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .width(200.dp)
                )
                {
                    Text(text = "Add Event")
                }
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

@Composable
fun EventCategoryField(eventCategory: String, onEventCategoryChange: (String) -> Unit) {
    TextField(
        value = eventCategory,
        onValueChange = onEventCategoryChange,
        label = { Text(text = "Event Category") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun EventDateField(eventDate: Date, onEventDateChange: (Date) -> Unit) {
    val selectedDate = remember { mutableStateOf(Calendar.getInstance()) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Event Date")
        Button(
            onClick = {
                showDatePicker(selectedDate.value, onEventDateChange, context)
            }
        ) {
            Text(text = "Select Date")
        }
    }
}

@Composable
fun EventStartDateField(eventStartDate: String, onEventStartDateChange: (String) -> Unit) {
    TextField(
        value = eventStartDate,
        onValueChange = onEventStartDateChange,
        label = { Text(text = "Event Start Date") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun EventEndDateField(eventEndDate: String, onEventEndDateChange: (String) -> Unit) {
    TextField(
        value = eventEndDate,
        onValueChange = onEventEndDateChange,
        label = { Text(text = "Event End Date") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

fun showDatePicker(initialDate: Calendar, onDateSelected: (Date) -> Unit, context: Context) {
    val year = initialDate.get(Calendar.YEAR)
    val month = initialDate.get(Calendar.MONTH)
    val day = initialDate.get(Calendar.DAY_OF_MONTH)

    val listener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        onDateSelected(calendar.time)
    }

    DatePickerDialog(context, listener, year, month, day).show()
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
fun EventRegistrationField(eventRegistration: String, onEventRegistrationChange: (String) -> Unit) {
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
fun SpeakersField(eventSpeakers: Array<String>, onEventSpeakersChange: (Array<String>) -> Unit) {
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
fun EventPrerequisitesField(eventPrerequisites: String, onEventPrerequisitesChange: (String) -> Unit) {
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
fun EventCancellationPolicyField(eventCancellationPolicy: String, onEventCancellationPolicyChange: (String) -> Unit) {
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
fun EventAccessibilityField(eventAccessibility: String, onEventAccessibilityChange: (String) -> Unit) {
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




