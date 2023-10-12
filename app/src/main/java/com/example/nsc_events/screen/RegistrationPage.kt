package com.example.nsc_event.screen

import android.widget.RadioGroup
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nsc_event.R
import com.example.nsc_event.Routes
import com.example.nsc_events.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable


fun SignUpPage(navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = stringResource(id = R.string.app_logo),
                    modifier = Modifier.size(100.dp)
                )

                TextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = {
                        Text(
                            text = stringResource(id = R.string.first_name),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                val isLastNameValid = lastName.isNotBlank()
                if (!isLastNameValid) {
                    Text(
                        text = "Please enter a valid last name",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                TextField(
                    value = studentId,
                    onValueChange = { studentId = it },
                    label = { Text("Student ID Number") },
                    // Add any other necessary TextField properties
                )
                //student and faculty
                RadioGroup(
                    selectedOption = role,
                    options = listOf("Student", "Faculty"),
                    onSelectedChange = { role = it },
                    // Add any other necessary RadioGroup properties
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            text = stringResource(id = R.string.email),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                    isError = !isEmailValid,
                    trailingIcon = {
                        if (!isEmailValid) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_error),
                                contentDescription = stringResource(id = R.string.error)
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Button(
                    onClick = { /* Perform sign-up logic */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = stringResource(id = R.string.sign_up))
                }
            }
        }
    }
}