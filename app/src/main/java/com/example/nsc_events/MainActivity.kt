@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.nsc_events

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nsc_events.screen.ScreenMain
import com.example.nsc_events.ui.theme.Nsc_eventsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Nsc_eventsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //ScreenMain()
                    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(text = "My NSC Events")
                                },
                                navigationIcon = {
                                    IconButton(onClick = { /*TODO pop the nav stack*/ }) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "Go back"
                                        )
                                     }
                                },
                                actions = {
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Ic2BGsmdbf529&)25
                                        on(
                                            imageVector = Icons.Default.FavoriteBorder,
                                            contententDescription = "Mark as favorite"
                                        )
                                    }

                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contententDescription = "Edit my events"
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
                            items(100) {
                                Text(
                                    text = "Item$it",
                                    modifier = Modifier.padding(16.dp)
                                    )
                            }
                        }


                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Nsc_eventsTheme {
        ScreenMain()
    }
}