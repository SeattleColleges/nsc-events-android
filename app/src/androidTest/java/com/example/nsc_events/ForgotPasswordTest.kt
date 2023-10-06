package com.example.nsc_events

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nsc_events.screen.ForgotPasswordPage
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)  // To indicate that we've to run it with AndroidJUnit runner
class ForgotPasswordTest {
    @get: Rule
    val rule =
        createComposeRule()   // compose rule is required to get access to the composable component

    lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        rule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            ForgotPasswordPage(navController = navController)
        }
    }

    @Test
    fun verify_StartDestinationIsForgotPasswordScreen() {
        val idToString = R.string.forgot_password_button_text.toString()
        rule
            .onNodeWithText("Forgot Password?")
            .assertIsDisplayed()
    }

    @Test
    fun emailNotInHierarchy() {
        // Check that the email address is not in the hierarchy.
        Espresso.onView(withText(R.string.forgot_password_email)).check(doesNotExist())
    }
}



