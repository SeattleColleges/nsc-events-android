package com.example.nsc_events.data.network.service

import com.example.nsc_events.data.network.HttpRoutes
import com.example.nsc_events.data.network.dto.event_dto.EventDeleteResponse
import com.example.nsc_events.data.network.dto.event_dto.EventRequest
import com.example.nsc_events.data.network.dto.event_dto.EventResponse
import com.example.nsc_events.data.network.dto.event_dto.EventUpdateRequest
import com.example.nsc_events.data.network.dto.event_dto.EventUpdateResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json

// handling network operations for creating, updating, and/or deleting events

class EventServiceImpl(private val client: HttpClient) : EventService {

    @OptIn(InternalAPI::class)
    override suspend fun createNewEvent(eventRequest: EventRequest): EventResponse? {
        return try {
            val response = client.post {
                url(HttpRoutes.CREATE_EVENTS)
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                body = Json.encodeToString(EventRequest.serializer(), eventRequest)
            }
            response.body()
        } catch (e: RedirectResponseException) {
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            println("Error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }


    override suspend fun updateAnEvent(eventUpdateRequest: EventUpdateRequest) : EventUpdateResponse? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAnEvent(eventRequest: EventRequest): EventDeleteResponse? {
        TODO("Not yet implemented")
    }


}