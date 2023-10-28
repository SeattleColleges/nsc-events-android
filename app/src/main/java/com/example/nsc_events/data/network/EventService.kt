package com.example.nsc_events.data.network

import com.example.nsc_events.data.network.dto.EventResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface EventService {

    suspend fun getEvents(): List<EventResponse>

    suspend fun getEventsById(id: Int): EventResponse

    companion object {
        fun create() : EventService {
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
                    // TODO: auth
                }
            )
        }
    }

}