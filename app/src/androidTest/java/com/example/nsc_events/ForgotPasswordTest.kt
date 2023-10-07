package com.example.nsc_events

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nsc_events.screen.ForgotPasswordPage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)  // To indicate that we've to run it with AndroidJUnit runner
class ForgotPasswordTest {
    @get: Rule
    val rule =
        createComposeRule()   // compose rule is required to get access to the composable component

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        rule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            ForgotPasswordPage(navController = navController)
        }
    }

    @Test
    fun verifyStartDestinationIsForgotPasswordScreen() {
        rule
            .onNodeWithText("Forgot Password?")
            .assertIsDisplayed()
    }

    @Test
    fun verifyTextEnteredIntoEmail() {
        rule
            .onNodeWithText("Email").performTextInput("1")
        rule
            .onNodeWithText("Email").assertTextContains("1")
    }
}



