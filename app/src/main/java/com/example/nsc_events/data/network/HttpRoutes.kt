package com.example.nsc_events.data.network

object HttpRoutes {
    private const val BASE_URL = "http://10.0.2.2:3000/api"
    const val SIGNUP = "$BASE_URL/auth/signup"
    const val LOGIN = "$BASE_URL/auth/login"
    const val FORGOT_PASSWORD = "$BASE_URL/auth/forgot-password"
}