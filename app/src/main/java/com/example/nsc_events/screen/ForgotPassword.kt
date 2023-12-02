package com.example.nsc_events.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nsc_events.MainActivity
import com.example.nsc_events.R
import com.example.nsc_events.Routes
import com.example.nsc_events.data.network.auth.ForgotPasswordService
import com.example.nsc_events.data.network.dto.auth_dto.ForgotPasswordRequest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ForgotPasswordPage(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val current = LocalContext.current

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Login")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.Login.route) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back to Login page"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(values),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.packaging),
                contentDescription = stringResource(id = R.string.login_logo_description),
                modifier = Modifier
                    .size(50.dp)
            )
            Text(
                text = stringResource(id = R.string.forgot_password_title),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Light,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                ),
                modifier = Modifier
                    .wrapContentSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.small
                        ),
                ) {
                    // Email field
                    TextField(
                        value = email,
                        onValueChange = {
                            email = it
                            isEmailValid = validateEmail(it)
                        },
                        isError = !isEmailValid,
                        label = { Text(text = stringResource(id = R.string.forgot_password_email)) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 16.dp)
                    )
                    // Email display error
                    if (!isEmailValid) {
                        ErrorDisplay(text = stringResource(id = R.string.forgot_password_email_error))
                    }

                    // Submit button
                    Button(
                        onClick = {
                            keyboardController?.hide()
                            coroutineScope.launch {
                                if (isEmailValid) {
                                    resetToken(email, current)
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .width(200.dp)
                            .align(Alignment.CenterHorizontally),
                    ) {
                        Text(
                            text = stringResource(id = R.string.forgot_password_button_text).uppercase(),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                }
            }
        }
    }
}

// Reset token function
suspend fun resetToken(email: String, current: Context) {
    val forgotPasswordRequest = ForgotPasswordRequest(email)
    val forgotPasswordResponse =
        ForgotPasswordService.create().forgotPassword(forgotPasswordRequest)
    try {
        if (forgotPasswordResponse != null) {
            MainActivity.getPref().edit().putString("token", forgotPasswordResponse.newToken)
                .apply()
            Toast.makeText(
                current,
                R.string.forgot_password_success_reset_token,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(current, R.string.forgot_password_invalid_email, Toast.LENGTH_SHORT)
                .show()
        }
    } catch (e: Exception) {
        Toast.makeText(current, R.string.forgot_password_invalid_email, Toast.LENGTH_SHORT).show()
    }
}





