package com.example.nsc_events.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nsc_events.Routes

@Composable
fun ScreenMain(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HomePage.route) {

        composable(Routes.HomePage.route) {
            HomePage(navController  = navController)
        }
        composable(Routes.Login.route) {
            LoginPage(navController = navController)
        }
        composable(Routes.SignUp.route) {
            SignUpPage(navController = navController)
        }
        composable(Routes.AddEvent.route) {
            AddEventPage(navController = navController)
        }
        composable(Routes.ForgotPassword.route) {
            ForgotPasswordPage(navController = navController)
        }
    }
}