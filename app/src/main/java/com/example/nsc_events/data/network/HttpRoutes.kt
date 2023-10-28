package com.example.nsc_events.data.network

object HttpRoutes {
    // question how to determine the ip address?
    private const val BASE_URL = "http://10.0.2.2:3000/api"
    const val EVENTS = "$BASE_URL/events"
    const val EVENT = "$BASE_URL/events/{id}"
}