package com.example.nsc_events.model

import com.example.nsc_events.data.network.dto.auth_dto.Role

data class User(
    val userFirstName: String,
    val userLastName: String,
    val userEmail: String,
    val userRole: Role,
    val userId: String = "0"
)