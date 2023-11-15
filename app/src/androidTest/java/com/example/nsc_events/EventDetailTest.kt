package com.example.nsc_events


import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nsc_events.screen.EventDetailPage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)  // To indicate that we've to run it with AndroidJUnit runner
class EventDetailTest {
    @get: Rule
    val rule =
        createComposeRule()   // compose rule is required to get access to the composable component

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        rule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            EventDetailPage(navController = navController, "Thanksgiving Pageant")
        }
    }

    @Test
    fun checkNumberOfMatchedNodesOnEventDetailPage() {
        rule
            .onAllNodesWithText("Thanksgiving Pageant")
            .assertCountEquals(1)
    }

    @Test
    fun assertBackButtonIsClickableOnEventDetailPage() {
        rule
            .onNodeWithContentDescription("Back to Home page")
            .assertHasClickAction()
    }

    @Test
    fun assertHomeHasNoClickActionOnEventDetailPage() {
        rule
            .onNodeWithText("Home")
            .assertHasNoClickAction()
    }

    @Test
    fun assertAttendButtonIsDisplayedAndClickableOnEventDetailPage() {
        rule
            .onNodeWithText("Attend")
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun assertEditAndDeleteButtonsAreClickableOnEventDetailPage() {
        rule
            .onNodeWithContentDescription("Edit")
            .assertHasClickAction()
        rule
            .onNodeWithContentDescription("Delete")
            .assertHasClickAction()
    }
}
