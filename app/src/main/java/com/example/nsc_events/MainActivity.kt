package com.example.nsc_events

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nsc_events.screen.ScreenMain
import com.example.nsc_events.ui.theme.Nsc_eventsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        pref = getSharedPreferences("nscHidden", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContent {
            Nsc_eventsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenMain()
                }
            }
        }
    }

    companion object {
        private lateinit var pref: SharedPreferences
        fun getPref(): SharedPreferences {
            return pref
        }

        fun getToken(): String? {
            return pref.getString("token", null)
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