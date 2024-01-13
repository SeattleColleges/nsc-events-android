package com.example.nsc_events

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nsc_events.screen.HomePage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)  // To indicate that we've to run it with AndroidJUnit runner
class HomePageTest {
    @get: Rule
    val rule =
        createComposeRule()   // compose rule is required to get access to the composable component

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        rule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
          //  HomePage(navController = navController)
        }
    }

    @Test
    fun checkNumberOfMatchedNodesOnHomePage() {
        rule
            .onAllNodesWithText("Welcome to NSC Events")
            .assertCountEquals(1)
    }

    @Test
    fun assertAddEventIsClickableOnHomePage() {
        rule
            .onNodeWithText("Add Event")
            .assertHasClickAction()
    }
}