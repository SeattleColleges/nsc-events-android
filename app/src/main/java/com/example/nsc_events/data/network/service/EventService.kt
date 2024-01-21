package com.example.nsc_events.data.network.service

import com.example.nsc_events.MainActivity
import com.example.nsc_events.data.network.dto.event_dto.EventDeleteResponse
import com.example.nsc_events.data.network.dto.event_dto.EventRequest
import com.example.nsc_events.data.network.dto.event_dto.EventResponse
import com.example.nsc_events.data.network.dto.event_dto.EventUpdateRequest
import com.example.nsc_events.data.network.dto.event_dto.EventUpdateResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface EventService {

    suspend fun createNewEvent(eventRequest: EventRequest) : EventResponse?

    suspend fun updateAnEvent(eventUpdateRequest: EventUpdateRequest) : EventUpdateResponse?

    suspend fun deleteAnEvent(eventRequest: EventRequest) : EventDeleteResponse?


    companion object {
        fun create() : EventServiceImpl {

            val token = MainActivity.getToken() ?: ""

            return EventServiceImpl(
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

                    // adding a default request interceptor
                    defaultRequest {
                        if (token.isNotEmpty()) {
                            header("Authorization", "Bearer $token")
                        }
                    }

                }
            )
        }
    }

}