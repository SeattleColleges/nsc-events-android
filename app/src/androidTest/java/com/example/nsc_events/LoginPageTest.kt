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
import com.example.nsc_events.screen.LoginPage
import org.junit.Before import org.junit.Rule
import org.junit.Test import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) class LoginPage {

    @get: Rule val rule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before fun setUp() { rule.setContent { navController = TestNavHostController(LocalContext.current)
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        LoginPage(navController = navController) } }

    @Test
    fun checkNumberOfMatchedNodesOnLoginPage()
    { rule .onAllNodesWithText("Login") .assertCountEquals(1) }

    @Test
    fun assertEmailTextFieldIsPresentOnLoginPage()
    { rule .onNodeWithText("Email") .assertExists() }

    @Test
    fun assertPasswordTextFieldIsPresentOnLoginPage()
    { rule .onNodeWithText("Password") .assertExists() }

    @Test
    fun assertLoginButtonIsClickableOnLoginPage()
    { rule .onNodeWithText("Login") .assertHasClickAction() }

    @Test
    fun assertForgotPasswordIsClickableOnLoginPage()
    { rule .onNodeWithText("Forgot password?") .assertHasClickAction() }

    @Test
    fun assertCreateAccountIsClickableOnLoginPage()
    { rule .onNodeWithText("Create account") .assertHasClickAction() } }
