package com.example.nsc_events.data.network.auth

import com.example.nsc_events.data.network.HttpRoutes
import com.example.nsc_events.data.network.dto.auth_dto.AttendeeDto
import io.ktor.client.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json

class AttendServiceImpl(
    private val client: HttpClient,
    private val getToken: suspend () -> String
) : AttendService {
    @OptIn(InternalAPI::class)
    override suspend fun attendEvent(eventId: String, token: String, attendee: AttendeeDto): HttpResponse {
        val authToken = getToken()
        val url = "${HttpRoutes.ATTEND_EVENT}/$eventId"

        return try {
            client.post(url) {
                header(HttpHeaders.Authorization, "Bearer $authToken")
                contentType(ContentType.Application.Json)
                body = Json.encodeToString(AttendeeDto.serializer(), attendee)
            }
        } catch (e: RedirectResponseException) {
            println("Redirect error: ${e.response.status.description}")
            throw e
        } catch (e: ClientRequestException) {
            println("Client request error: ${e.response.status.description}")
            throw e
        } catch (e: ServerResponseException) {
            println("Server response error: ${e.response.status.description}")
            throw e
        } catch (e: Exception) {
            println("Error attending event: ${e.message}")
            throw e
        }
    }
}