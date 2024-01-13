package com.example.nsc_events.screen

import android.content.Context
import android.net.http.HttpException
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.nsc_events.data.Datasource
import com.example.nsc_events.data.network.auth.DeleteService
import com.example.nsc_events.data.network.dto.auth_dto.DeleteRequest
import com.example.nsc_events.data.network.dto.auth_dto.Role
import com.example.nsc_events.model.DataState
import com.example.nsc_events.model.Event
import com.example.nsc_events.model.EventsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailPage(navController: NavController, eventId: String) {

    val eventsViewModel: EventsViewModel = viewModel()
    val eventsState = eventsViewModel.eventsState.collectAsState().value


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            /* Back arrow that navigates back to login page */
            TopAppBar(
                title = { Text("Home") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Routes.HomePage.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Home page"
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

            // Other states
            var isDelete by remember { mutableStateOf(false) }
            // val coroutineScope = rememberCoroutineScope()
            // val current = LocalContext.current

            when (eventsState) {
                is DataState.Loading -> {
                    // TODO: Show loading UI
                }
                is DataState.Success -> {
                    // Find the event by eventId
                    eventsState.data.find { it.eventId == eventId }?.let { event ->
                        EventDetailCard(event = event, navController = navController)
                    } ?: Text("Event not found")
                }
                is DataState.Error -> {
                    // TODO: Handle error state
                }
            }
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    isDelete = !isDelete
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = "Delete"
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
                }

                Spacer(modifier = Modifier.padding(16.dp))
                Button(onClick = {
                    navController.navigate("${Routes.EventEdit.route}/$eventId")
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = "Edit"
                    )
                }
            }
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(2.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    // TODO: save this locally or in teh cloud
                }) {
                    Text("Attend")
                }
            }
        }
    }
}

@Composable
fun EventDetailCard(event: Event, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO: Change me into actual images
            Image(
                painter = painterResource(id = R.drawable.placeholder_image),
                contentDescription = stringResource(id =  R.string.event_cover_photo_description),
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
            )
            Text(
                text = "eventTitle: ${event.eventTitle}",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                ),
                modifier = Modifier
                    .wrapContentSize()
            )
            Text(text = "eventDescription: ${event.eventDescription}")
            Text(text = "eventDate: ${event.eventDate}")
            Text(text = "eventStartTime: ${event.eventStartTime}")
            Text(text = "eventEndTime: ${event.eventEndTime}")
            Text(text = "eventLocation: ${event.eventLocation}")
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