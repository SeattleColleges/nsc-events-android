package com.example.nsc_events.data.network

import com.example.nsc_events.data.network.dto.EventResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get

class EventServiceImpl (
    private val client: HttpClient
) : EventService {

    override suspend fun getEvents(): List<EventResponse> {
        return try {
            val response : EventResponse = client.get(HttpRoutes.EVENTS).body()
            listOf(response)
        } catch (e: RedirectResponseException) {
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: ClientRequestException) {
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: ServerResponseException) {
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: Exception) {
            println("Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getEventsById(id: Int): EventResponse {
        return client.get (HttpRoutes.EVENTS + "/$id").body()
    }
}
