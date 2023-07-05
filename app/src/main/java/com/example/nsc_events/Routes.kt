package com.example.nsc_events

sealed class Routes(val route: String) {
    object Login : Routes("Login")
    object SignUp : Routes("SignUp")
}