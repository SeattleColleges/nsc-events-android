package com.example.nsc_events.screen

import android.content.Context
import android.net.http.HttpException
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nsc_events.MainActivity
import com.example.nsc_events.R
import com.example.nsc_events.Routes
import com.example.nsc_events.data.network.auth.SignUpService
import com.example.nsc_events.data.network.dto.auth_dto.Role
import com.example.nsc_events.data.network.dto.auth_dto.SignUpRequest
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpPage( navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }
    var passwordMatchValid by remember { mutableStateOf(true) }
    var matchPasswordSupportingText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val current = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { navController.navigate(Routes.HomePage.route) },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Home")
        }

        Text ( text = "Sign Up")

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
                    )
            ) {
                // TODO: Name Field here
                TextField(value = name, onValueChange ={
                    name = it
                },
                    label = { Text(text = "Name")},
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 16.dp)
                    )

                // Email field
                TextField(value = email, onValueChange ={
                    email = it
                    isEmailValid = validateEmail(email)
                },
                    isError = !isEmailValid,
                    label = { Text(text = "Email")},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email
                    ),
                    maxLines = 1,
                    supportingText = {
                                     if(isEmailValid) {
                                         Text(text = "")
                                     } else {

                                         Text(text = "Email is not val!", color = MaterialTheme.colorScheme.error)
                                     }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 16.dp)
                )

                // Password field
                TextField(value = password, onValueChange = {
                    password = it
                    isPasswordValid = validatePassword(password)
                },
                    isError = !isPasswordValid,
                    label = {Text(text = "Password")},
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    ),
                    maxLines = 1,
                    supportingText = { if(isPasswordValid){
                        Text(text = "")
                    } else {
                        Text(text = "Password is not valid!", color = MaterialTheme.colorScheme.error)
                    }},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 16.dp)
                )

                // confirmPassword field
                TextField(value = confirmPassword, onValueChange = {
                    confirmPassword = it
//                    isPasswordValid = validatePassword(confirmPassword)
                },
                    label = {Text(text = "Confirm Password")},
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    ),
                    isError = !passwordMatchValid,
                    maxLines = 1,
                    supportingText = { Text(text = matchPasswordSupportingText, color = MaterialTheme.colorScheme.error) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 16.dp)
                )

                Button(
                    onClick = {
                        keyboardController?.hide()
                        if (name.isNotBlank() && email.isNotBlank()
                            && password.isNotBlank() && confirmPassword.isNotBlank()
                            && password == confirmPassword
                            && isEmailValid && isPasswordValid) {
                            coroutineScope.launch {
                                signUp(name, email, password, navController, current)
                            }
                        } else {
                            passwordMatchValid = false
                            matchPasswordSupportingText = "Passwords must match!"
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .width(200.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        text = "SIGN UP",
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

suspend fun signUp(
    name: String,
    email: String,
    password: String,
    navController: NavHostController,
    current: Context
) {
    return try {
        val signUpRequest = SignUpRequest(
            name = name,
            email = email,
            password = password,
            role = Role.ADMIN
        )
        val signUpResponse = SignUpService.create().signup(signUpRequest)
        if (signUpResponse != null) {
            MainActivity.getPref().edit().putString("token", signUpResponse.token).apply()
            navController.navigate(Routes.Login.route)
            Toast.makeText(
                current,
                R.string.signup_successful_toast,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                current,
                R.string.signup_failed_toast,
                Toast.LENGTH_SHORT
            ).show()
        }
    } catch (e: HttpException) {
        e.printStackTrace()
    }
}