package com.example.nsc_events.data.network.dto.auth_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Role(val role: String) {
    @SerialName("admin")
    ADMIN("admin"),

    @SerialName("creator")
    CREATOR("creator"),

    @SerialName("user")
    USER("user")
}

@Serializable
data class SignUpRequest(
    @SerialName("name")
    val name: String,

    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,

    @SerialName("role")
    val role: Role
)
