package com.example.nsc_events.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        composable(Routes.EventDetail.route+"/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            EventDetailPage(navController = navController,
                eventId = backStackEntry.arguments!!.getString("eventId")!!,
            )
        }
        composable(
            route = "${Routes.EventEdit.route}/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            EventEditPage(
                navController = navController,
                eventId = backStackEntry.arguments!!.getString("eventId")!!
            )
        }
        composable(Routes.ForgotPassword.route) {
            ForgotPasswordPage(navController = navController)
        }
        composable(Routes.CreatorView.route) {
            CreatorView(navController = navController)
        }

        composable(Routes.AdminView.route) {
            AdminView(navController = navController)
        }
        composable(Routes.EditUserRole.route) {
            EditUserRole(navController = navController)
        }
    }
}