package com.example.nsc_events.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.nsc_events.Routes
import com.example.nsc_events.data.Datasource
import com.example.nsc_events.model.Event

@Composable
fun HomePage(navController: NavHostController) {
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
        Button(
            onClick = {
                navController.navigate(Routes.Login.route)
            }
        ) {
            Text(text = "Sign-in / Sign-up")

        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                navController.navigate(Routes.AddEvent.route)
            }
        ) {
            Text(text = "Add Event")
        }
        EventList(events = Datasource().loadProducts(), navController = navController)
    }
}

@Composable
fun EventCard(event: Event, navController: NavController) {
    Card(
        modifier = Modifier
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
//            Image(
//                painter = painterResource(id = event.image.toInt()),
//                contentDescription = stringResource(id = R.string.event_image_description),
//                modifier = Modifier
//                    .size(200.dp)
//                    .padding(16.dp),
//
//                )
            Text(
                text = "Name: ${event.eventName}",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
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
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(events) { event ->
            EventCard(event = event, navController = navController)
            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}