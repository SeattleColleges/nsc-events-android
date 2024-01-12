package com.example.nsc_events.data.network.auth

import com.example.nsc_events.data.network.HttpRoutes
import com.example.nsc_events.model.Event
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
class EventsServiceImpl(
    private val client: HttpClient
) : EventsService {
    override suspend fun getEvents(): List<Event> {
        return try {
            val response = client.get {
                url(HttpRoutes.EVENTS)
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }
            Json.decodeFromString<List<Event>>(response.bodyAsText())
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
}