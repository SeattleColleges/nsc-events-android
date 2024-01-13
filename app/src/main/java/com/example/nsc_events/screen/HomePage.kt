package com.example.nsc_events.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.nsc_events.MainActivity
import com.example.nsc_events.R
import com.example.nsc_events.Routes
import com.example.nsc_events.model.DataState
import com.example.nsc_events.model.Event
import com.example.nsc_events.model.EventsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavHostController, eventsViewModel: EventsViewModel) {
    val eventsState = eventsViewModel.eventsState.collectAsState()
    var eventsList = emptyList<Event>()

    when (val state = eventsState.value) {
        is DataState.Loading -> {
            // TODO: Handle loading state
        }
        is DataState.Success -> {
            eventsList = state.data
        }
        is DataState.Error -> {
            // TODO: Handle error state
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to NSC Events",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                color = if (isSystemInDarkTheme()) {
                    Color.White
                } else {
                    Color.Black
                }
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                    },
                    navigationIcon = {
                        // TODO: give this button an action when clicked
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open Menu"
                            )
                        }
                    },
                    actions = {
                        TextButton(onClick = { navController.navigate(Routes.Login.route) }) {
                            Text("Login", color = Color.Black)
                        }
                        // TODO maybe hide this button if the user doesn't have any events or can't edit them.
                        // TODO: give this button an action when clicked
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit my events"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { values ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
            }
            EventList(events = eventsList, navController = navController)
        }
    }
}
@Composable
fun CustomTextField(text: String, fontSize: TextUnit = 20.sp) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
        ),
        modifier = Modifier
            .wrapContentSize()
    )
}

@Composable
fun EventCard(event: Event, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable {
                navController.navigate(Routes.EventDetail.route+"/${event.eventId}")
            }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,


        ) {
            EventImage(event = event)
            Text(
                text = "eventTitle: ${event.eventTitle}",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                ),
                modifier = Modifier
                    .wrapContentSize()
            )
            Text(
                text = "eventCategory: ${event.eventCategory}",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Default,
                ),
                modifier = Modifier
                    .wrapContentSize()
            )
        }
    }
}

@Composable
fun EventList(events: List<Event>, navController: NavController) {
    val hidden = MainActivity.getPref().getStringSet("hidden", setOf(""))
    LazyColumn(
        modifier = Modifier
            .padding(top = 70.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(events
            .filter { !hidden!!.contains(it.eventId) }) { event ->
            EventCard(event = event, navController = navController)
            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}

// TODO: Get this working for actual images incoming from the S3 bucket
@Composable
fun EventImage(event: Event) {
    val imageResId = try {
        // TODO: Get this working
        event.eventCoverPhoto.toInt()
    } catch (e: Exception) {
        // Fallback resource ID for a placeholder image
        R.drawable.placeholder_image
    }

    Image(
        painter = painterResource(id = imageResId),
        contentDescription = stringResource(id = R.string.event_cover_photo_description),
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp)
    )
}



