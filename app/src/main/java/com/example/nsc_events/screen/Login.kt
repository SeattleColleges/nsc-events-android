package com.example.nsc_events.screen

import android.content.Context
import android.util.Base64
import android.util.Patterns
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nsc_events.MainActivity
import com.example.nsc_events.R
import com.example.nsc_events.Routes
import com.example.nsc_events.data.network.auth.LoginService
import com.example.nsc_events.data.network.dto.auth_dto.LoginRequest
import com.example.nsc_events.data.network.dto.auth_dto.Role
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginPage(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val current = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Home")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.HomePage.route) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back to Homepage"
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
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Image(
                painter = painterResource(id = R.drawable.packaging),
                contentDescription = stringResource(id = R.string.login_logo_description),
                modifier = Modifier
                    .size(50.dp)
            )

            Text(
                text = stringResource(id = R.string.login_title),
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
                        label = { Text(text = stringResource(id = R.string.login_email)) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 16.dp)
                    )
                    // Email display error
                    if (!isEmailValid) {
                        ErrorDisplay(text = stringResource(id = R.string.login_email_error))
                    }

                    // Password field
                    TextField(
                        value = password,
                        onValueChange = {
                            password = it
                            isPasswordValid = validatePassword(it)
                        },
                        isError = !isPasswordValid,
                        label = { Text(text = stringResource(id = R.string.login_password)) },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp, bottom = 20.dp),
                    )

                    // Password display error
                    if (!isPasswordValid) {
                        ErrorDisplay(text = stringResource(id = R.string.login_password_error))
                    }

                    // Login button
                    Button(
                        onClick = {
                            keyboardController?.hide()
                            // Check if email and password are valid
                            if (isEmailValid && isPasswordValid) {
                                // Login with valid credentials
                                coroutineScope.launch {
                                    loginWithValidCredentials(email, password, navController, current)
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .width(200.dp)
                            .align(Alignment.CenterHorizontally),
                    ) {
                        Text(
                            text = stringResource(id = R.string.login_button_text).uppercase(),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }

                    // Forgot password
                    ClickableText(
                        text = AnnotatedString("Forgot password?"),
                        onClick = {
                            navController.navigate(Routes.ForgotPassword.route)
                            // TODO: Add forgot password functionality
                        },
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            textDecoration = TextDecoration.Underline,
                            color = if (isSystemInDarkTheme()) Color.LightGray else Color.Blue
                        ),
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

            // Create a new account
            Box(modifier = Modifier.fillMaxSize()) {
                ClickableText(
                    text = AnnotatedString(text = stringResource(id = R.string.login_create_account)),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(20.dp),
                    onClick = { navController.navigate(Routes.SignUp.route) },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        textDecoration = TextDecoration.Underline,
                        color = if (isSystemInDarkTheme()) Color.LightGray else Color.Black
                    )
                )
            }


        }

    }
}

// Validation functions
fun validateEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email)
        .matches() && email.isNotEmpty() && email.isNotBlank() && email.length > 5 && email.length < 30 && email.contains(
        "@"
    )
}

fun validatePassword(password: String): Boolean {
    return password.isNotEmpty() && password.isNotBlank() && password.length > 5 && password.length < 30
}

// Error display function
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDisplay(text: String) {
    Text(
        text = text,
        color = Color.Red,
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily.Default,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, bottom = 8.dp)
    )

}

suspend fun loginWithValidCredentials(email: String, password: String, navController: NavHostController, current: Context) {
    // login with valid credentials
    try {
        val loginRequest = LoginRequest(email, password)
        val loginResponse = LoginService.create().login(loginRequest)
        if (loginResponse != null) {
            val token = loginResponse.token
            saveToken(token)

            val userRole = getUserRole(token)
            MainActivity.getPref().edit().putString("userRole", userRole.name).apply()

            MainActivity.getPref().edit().putString("token", loginResponse.token).apply()
            navController.navigate(Routes.AddEvent.route)
            Toast.makeText(
                current,
                "Welcome ${getName(loginResponse.token)} to NSC Events!",
                Toast.LENGTH_SHORT
            ).show()
            loginResponse.token
        } else {
            Toast.makeText(
                current,
                R.string.login_invalid_email_or_password_message,
                Toast.LENGTH_SHORT
            ).show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun getUserRole(token: String): Role {
    return try {
        val payload = token.split(".")[1]
        val decodedPayload = String(Base64.decode(payload, Base64.DEFAULT))
        val jsonObject = JSONObject(decodedPayload)
        val roleString = jsonObject.getString("role")
        Role.valueOf(roleString.uppercase(Locale.ROOT))
    } catch (e: Exception) {
        e.printStackTrace()
        Role.USER
    }
}

fun saveToken(token: String) {
    MainActivity.getPref().edit().putString("token", token).apply()
}

fun getName(token: String): String? {
    // decode jwt token to get name
    return try {
        val payload = token.split(".")[1]
        val decodedPayload = String(Base64.decode(payload, Base64.DEFAULT))
        val jsonObject = JSONObject(decodedPayload)
        jsonObject.getString("name")
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}