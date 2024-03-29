package com.example.nsc_events.data.network.auth

import com.example.nsc_events.MainActivity
import com.example.nsc_events.data.network.dto.auth_dto.AttendeeDto
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface AttendService {
    suspend fun attendEvent(eventId: String, token: String, attendee: AttendeeDto): HttpResponse

    companion object {
        fun create(): AttendService {
            return AttendServiceImpl(
                    client = HttpClient(Android) {
                        install(ContentNegotiation) {
                            json(Json {
                                prettyPrint = true
                                isLenient = true
                                ignoreUnknownKeys = true
                            })
                        }
                        install (Logging) {
                            level = LogLevel.ALL
                            logger = Logger.DEFAULT
                        }
                    },
                    getToken = suspend {
                        MainActivity.getPref().getString("token", "") ?: ""
                    }
                )
        }
    }
}