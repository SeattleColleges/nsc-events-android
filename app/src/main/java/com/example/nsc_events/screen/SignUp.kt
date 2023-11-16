package com.example.nsc_events.screen

import android.content.Context
import android.net.http.HttpException
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignUpPage(navController: NavHostController) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }
    var passwordMatchValid by remember { mutableStateOf(true) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    var matchPasswordSupportingText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val current = LocalContext.current

    var isFirstNameValid by remember { mutableStateOf(true) }
    var isLastNameValid by remember { mutableStateOf(true) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Login") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.Login.route) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sign Up",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
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
                            )
                    ) {

                        // First name field
                        TextField(
                            value = firstName, onValueChange = {
                                firstName = it
                            },

                            // todo: add actual First Name text into string resources values.xml folder
                            label = { Text(text = "First Name") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 16.dp)
                        )

                        // Last name field
                        TextField(
                            value = lastName, onValueChange = {
                                lastName = it
                            },
                            // todo: add actual Last Name text into string resources values.xml folder
                            label = { Text(text = "Last Name") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 30.dp, end = 30.dp, bottom = 16.dp)
                        )

                        // Email field
                        TextField(
                            value = email,
                            onValueChange = {
                                email = it
                                isEmailValid = validateEmail(email)
                            },
                            isError = !isEmailValid,
                            // todo: add actual Email text into string resources values.xml folder
                            label = { Text(text = "Email") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 30.dp, end = 30.dp, bottom = 16.dp)
                        )

                        // todo: add actual Invalid email text into string resources values.xml folder
                        if (!isEmailValid) {
                            ErrorDisplay(text = "Invalid email")
                        }


                        // Password field
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextField(
                                value = password,
                                onValueChange = {
                                    password = it
                                    isPasswordValid = validatePassword(it)
                                    passwordMatchValid =
                                        validateConfirmPassword(password, confirmPassword)
                                },
                                isError = !isPasswordValid,
                                // todo: add actual Password text into string resources values.xml folder
                                label = { Text(text = "Password") },
                                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 30.dp, end = 8.dp, bottom = 16.dp)
                            )

                            // Toggle button to show/hide password
                            IconButton(
                                onClick = { isPasswordVisible = !isPasswordVisible },
                            ) {
                                Icon(
                                    painter = if (isPasswordVisible) painterResource(R.drawable.baseline_visibility_on_24) else painterResource(
                                        R.drawable.baseline_visibility_off_24
                                    ),
                                    contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password",
                                    modifier = Modifier.padding(top = 15.dp),
                                )
                            }
                        }

                        // todo: add actual Invalid password text into string resources values.xml folder
                        if (!isPasswordValid) {
                            ErrorDisplay(text = "Invalid password")
                        }


                        // confirmPassword field
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextField(
                                value = confirmPassword,
                                onValueChange = {
                                    confirmPassword = it
                                    passwordMatchValid = validateConfirmPassword(password, it)
                                },
                                // todo: add actual Confirm Password text into string resources values.xml folder
                                label = { Text(text = "Confirm Password") },
                                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Password,
                                    // setting ImeAction to done since this is last field to complete
                                    imeAction = ImeAction.Done
                                ),
                                // Using KeyboardActions composable to define an action that should be taken when ImeAction.Done is triggered, in this case hiding the keyboard
                                keyboardActions = KeyboardActions(
                                    onDone = { keyboardController?.hide() }
                                ),
                                singleLine = true,
                                isError = !passwordMatchValid,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 30.dp, end = 8.dp, bottom = 16.dp)
                            )

                            // Toggle button to show/hide password
                            IconButton(
                                onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }
                            ) {
                                Icon(
                                    painter = if (isConfirmPasswordVisible) painterResource(R.drawable.baseline_visibility_on_24) else painterResource(
                                        R.drawable.baseline_visibility_off_24
                                    ),
                                    contentDescription = if (isConfirmPasswordVisible) "Hide Password" else "Show Password",
                                    modifier = Modifier.padding(top = 15.dp),
                                )
                            }
                        }

                        // todo: add actual Password do not match text into string resources values.xml folder
                        if (!passwordMatchValid) {
                            ErrorDisplay(text = "Password do not match")
                        }


                        Button(
                            onClick =
                            {
                              keyboardController?.hide()
                              if (firstName.isNotBlank() && validateEmail(email) && validatePassword(password) && validateConfirmPassword(password, confirmPassword)) {
                                coroutineScope.launch {
                                  signUp(firstName, email, password, navController, current)
                                }
                              } else {
                                // Update error flags to show error messages
                                isEmailValid = validateEmail(email)
                                isPasswordValid = validatePassword(password)
                                passwordMatchValid = validateConfirmPassword(password, confirmPassword)
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
    )
}


// Validation functions
fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
    return password.isNotEmpty() && password.isNotBlank() && password == confirmPassword
}

fun validateFirstName(name: String): Boolean {
    // Add your validation logic here
    return name.isNotBlank()
}

fun validateLastName(name: String): Boolean {
    // Add your validation logic here
    return name.isNotBlank()
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
