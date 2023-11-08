package com.example.nsc_events

sealed class Routes(val route: String) {
    object Login : Routes("Login")
    object SignUp : Routes("SignUp")

    object AddEvent: Routes("Add Event")
    object HomePage: Routes("Home")
    object ForgotPassword: Routes("Forgot Password")

    object CreatorView: Routes("Creator View")
}