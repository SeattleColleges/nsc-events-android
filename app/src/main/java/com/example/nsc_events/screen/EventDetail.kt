package com.example.nsc_events.screen

import android.content.Context
import android.net.http.HttpException
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nsc_events.MainActivity
import com.example.nsc_events.R
import com.example.nsc_events.Routes
import com.example.nsc_events.data.network.auth.DeleteService
import com.example.nsc_events.data.network.dto.auth_dto.DeleteRequest
import com.example.nsc_events.data.network.dto.auth_dto.Role
import com.example.nsc_events.model.DataState
import com.example.nsc_events.model.Event
import com.example.nsc_events.model.EventsViewModel
import android.util.Base64
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.PopupProperties
import com.example.belindas_closet.ui.theme.md_theme_light_background
import org.json.JSONObject

// TODO: Extract these into a utility function in another folder for use across the app..
fun getToken(): String? {
    return MainActivity.getPref().getString("token", null)
}
fun extractRoleFromJwt(token: String?): String? {
    if (token.isNullOrBlank()) return null

    return try {
        val splitToken = token.split(".")
        if (splitToken.size >= 2) {
            val base64EncodedBody = splitToken[1]
            val decodedBody = Base64.decode(base64EncodedBody, Base64.URL_SAFE)
            val body = String(decodedBody, Charsets.UTF_8)
            val jsonObject = JSONObject(body)
            jsonObject.getString("role")
        } else null
    } catch (e: Exception) {
        // TODO: Handle exceptions
        null
    }
}
// END TODO


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailPage(navController: NavController, eventId: String) {
    // Retrieving viewModel and state from main
    val eventsViewModel: EventsViewModel = viewModel()
    val eventsState = eventsViewModel.eventsState.collectAsState().value

    // Get token from mainPref
    val token = getToken()
    val role = extractRoleFromJwt(token)

    // Extract Event from eventId
    var savedEvent: Event? = null

    when (eventsState) {
        is DataState.Loading -> {
            // TODO: Show loading UI
        }
        is DataState.Success -> {
            savedEvent = eventsState.data.find { it.eventId == eventId }
        }
        is DataState.Error -> {
            // TODO: Handle error state
        }
    }

    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(savedEvent?.eventTitle ?: "")},
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(Routes.HomePage.route) }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back to Home page"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    actions = {
                        // Only show the admin tool icons if the user is an Admin
                        if (role == "admin") {
                            AdminComponent(eventId = eventId, navController = navController)
                        }
                    }
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    modifier = Modifier,
                    onClick = { /* TODO */ },
                    icon = { Icon(Icons.Filled.Favorite, "Attend Event!", tint = Color.Red)},
                    text = { Text(text = "Attend Event!") },
                    elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 4.dp)
                )},

    ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        savedEvent?.let { event ->
                            EventDetailCard(event = event, navController = navController)
                        }
                        Button(onClick = {
                            // TODO: Implement your action here
                        }) {
                            Text("Attend")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventDetailCard(event: Event, navController: NavController) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO: Change me into actual images
            Image(
                painter = painterResource(id = R.drawable.placeholder_image),
                contentDescription = stringResource(id =  R.string.event_cover_photo_description),
                modifier = Modifier
                    .size(400.dp)
                    .padding(16.dp),
            )
            Text(
                text = " ${event.eventTitle} ",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                ),
                modifier = Modifier
                    .wrapContentSize()
            )
            Text(text = " ${event.eventDescription } ")
            Text(text = "Category: ${event.eventCategory}")
            Text(text = "Date: ${event.eventDate}")
            Text(text = "Starting Time: ${event.eventStartTime}")
            Text(text = "eventEndTime: ${event.eventEndTime}")
            Text(text = "eventLocation: ${event.eventLocation}")
            Text(text = "Event Host: ${event.eventHost}")
            event.eventWebsite?.let { Text(text = "Event Website: $it") }
            Text(text = "Event Registration: ${event.eventRegistration}")
            Text(text = "Event Capacity: ${event.eventCapacity}")
            Text(text = "Event Cost: ${event.eventCost}")
            Text(text = "Event Tags : ${event.eventTags.joinToString(", ")}")
            Text(text = "Event Schedule: ${event.eventSchedule}")
            Text(text = "Event Speakers: ${event.eventSpeakers.joinToString(", ")}")
            event.eventPrerequisites?.let { Text(text = "Event Prerequisites: $it") }
            event.eventCancellationPolicy?.let { Text(text = "Event Cancellation Policy: $it") }
            Text(text = "Event Contact: ${event.eventContact}")
            event.eventSocialMedia?.let { socialMedia ->
                Text(text = "Social Media: ${socialMedia.facebook}, ${socialMedia.twitter}, ${socialMedia.instagram}")
            }
            event.eventPrivacy?.let { Text(text = "Event Privacy: $it") }
            Text(text = "Event Accessibility: ${event.eventAccessibility}")
            Text(text = "Attendance Count: ${event.count}")
            Text(text = "Last Updated: ${event.updatedAt}")
            Text(text = "Created By: ${event.createdByUser}")
            Text(text = "Created At: ${event.createdAt}")
        }
    }
}

@Composable
fun ConfirmationDialogIndividual(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .border(1.dp, Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.delete_confirm_text))
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

suspend fun delete(eventId: String, navController: NavController, current: Context): Boolean {
    return try {
        val userRole = MainActivity.getPref().getString("userRole", Role.USER.name)?.let {
            Role.valueOf(it) } ?: Role.USER
        val deleteRequest = DeleteRequest(
            id = eventId,
            role = Role.ADMIN
        )
        val deleteResponse = DeleteService.create().delete(deleteRequest)
        if (userRole != Role.ADMIN) {
            Toast.makeText(current, R.string.unauthorized_toast, Toast.LENGTH_SHORT).show()
            false
        } else if (eventId.count() != 24) {
            Toast.makeText(current, R.string.invalid_id_toast, Toast.LENGTH_SHORT).show()
            false
        } else if (deleteResponse != null) {
            MainActivity.getPref().edit().putBoolean("isHidden", deleteResponse.isHidden).apply()
            navController.navigate(Routes.HomePage.route)
            Toast.makeText(current, R.string.delete_successful_toast, Toast.LENGTH_SHORT).show()
            true
        } else {
            Toast.makeText(current, R.string.delete_failed_toast, Toast.LENGTH_SHORT).show()
            false
        }
    } catch (e: HttpException) {
        e.printStackTrace()
        println("Error: ${e.message}")
        Toast.makeText(current, "Delete failed. Error: ${e.message}", Toast.LENGTH_SHORT).show()
        false
    }
}

@Composable
fun AdminComponent(eventId: String, navController: NavController) {
    var isDelete by remember { mutableStateOf(false) }
            IconButton(onClick = {
                isDelete = !isDelete
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Black
                )
            }
//                if (isDelete) {
//                    ConfirmationDialogIndividual(onConfirm = {
//                        coroutineScope.launch {
//                            val isDeleteSuccessful = delete(event.id, navController, current)
//                            if (isDeleteSuccessful) {
//                                val hidden = MainActivity.getPref().getStringSet("hidden", mutableSetOf(event.id))
//                                hidden?.add(event.id)
//                                val editor = MainActivity.getPref().edit()
//                                editor.putStringSet("hidden", hidden)
//                                editor.apply()
//                            }
//                        }
//                        // Remove the event from the database
//                        isDelete = false
//                    }, onDismiss = {
//                        isDelete = false
//                    })
//                }
            IconButton(onClick = {
                navController.navigate("${Routes.EventEdit.route}/$eventId")
            }) {
                Icon(
                    imageVector = Icons.Sharp.Edit, contentDescription = "Edit", tint = Color.Black
                )
            }
}