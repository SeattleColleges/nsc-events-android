import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SignUpPage(navController: NavHostController) {
    var firstName by remember {mutableStateOf("")}
    var lastName by  remember {mutableStateOf("")}
    var idNumber by remember {mutableStateOf("")}
    var studentOrFaculty by remember { mutableStateOf("")}
    var emailAddress by remember {mutableStateOf("")}
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text(text = "First Name") }
        )
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(text = "Last Name") }
        )
        TextField(
            value = idNumber,
            onValueChange = { idNumber = it },
            label = { Text(text = "Student ID Number") }
        )
        TextField(
            value = studentOrFaculty,
            onValueChange = { studentOrFaculty = it },
            label = { Text(text = "Student or Faculty") }
        )
        TextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            label = { Text(text = "Email Address") }
        )
        Button(
            onClick = { /* TODO: Handle form submission */ },
            content = { Text(text = "Submit") }
        )
    }
}