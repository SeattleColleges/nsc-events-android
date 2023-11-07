import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SignUpPage(navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var idNumber by remember { mutableStateOf("") }
    var studentOrFaculty by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text(text = "First Name") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(text = "Last Name") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = idNumber,
            onValueChange = { idNumber = it },
            label = { Text(text = "Student ID Number") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = studentOrFaculty,
            onValueChange = { studentOrFaculty = it },
            label = { Text(text = "Student or Faculty") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            label = { Text(text = "Email Address") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(text = "Confirm Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: Handle form submission */ },
            content = { Text(text = "Create Account") }
        )
    }
}
@Composable
fun LoginPage(navController: NavHostController) {
    // Your existing login page code here

    IconButton(
        onClick = { navController.popBackStack() },
        content = { Icon(Icons.Filled.ArrowBack, contentDescription = "Back") }
    )
}