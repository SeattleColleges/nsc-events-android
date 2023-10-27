package com.example.nsc_events.screen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nsc_events.R
import com.example.nsc_events.Routes
import com.example.nsc_events.data.Datasource
import com.example.nsc_events.model.Event
import dev.chrisbanes.accompanist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailPage(navController: NavController, eventId: String) {
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
            CustomTextField(text = eventId)
            val event = Datasource().loadEvents().find { it.eventTitle == eventId }!!
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(onClick = {
                    // TODO: delete functionality
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = "Delete"
                    )
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Button(onClick = {
                    // TODO: delete functionality
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = "Edit"
                    )
                }
            }


            EventDetailCard(event = event, navController = navController)
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    // TODO: increment the number of attendees
                }) {
                    Text("Attend")
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun photoSelector() {
    var imageUriState = mutableStateOf<Uri?>(null)
    val selectImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imageUriState.value = uri
        }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(100.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            if (imageUriState.value != null) {
                GlideImage(data = imageUriState.value!!)
            }

            Button(
                onClick = { selectImageLauncher.launch("image/*") },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Attach Photo")
            }

        }
    }
}

@Composable
fun EventDetailCard(event: Event, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate(Routes.EventDetail.route)
            },
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = event.eventCoverPhoto.toInt()),
                contentDescription = stringResource(id = R.string.event_cover_photo_description),
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
            )
            photoSelector()
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