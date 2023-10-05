package pages

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {
    private var firstNameEditText: EditText? = null
    private var lastNameEditText: EditText? = null
    private var studentIdEditText: EditText? = null
    private var studentOrFacultySpinner: Spinner? = null
    private var emailEditText: EditText? = null
    private var registerButton: Button? = null
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Initialize UI elements
        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        studentIdEditText = findViewById(R.id.studentIdEditText)
        studentOrFacultySpinner = findViewById(R.id.studentOrFacultySpinner)
        emailEditText = findViewById(R.id.emailEditText)
        registerButton = findViewById(R.id.registerButton)

        // Initialize the spinner with options
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.student_or_faculty_options, R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        studentOrFacultySpinner!!.adapter = adapter

        // Set a listener for the spinner
        studentOrFacultySpinner!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                // Handle the selected option
                val selectedOption = parentView.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing
            }
        }

        // Set a click listener for the register button
        registerButton!!.setOnClickListener {
            // Validate and process user input here
            val firstName = firstNameEditText!!.text.toString()
            val lastName = lastNameEditText!!.text.toString()
            val studentId = studentIdEditText!!.text.toString().toInt()
            val selectedOption = studentOrFacultySpinner!!.selectedItem.toString()
            val email = emailEditText!!.text.toString()

            // Perform validation and registration logic here
            if (isValidInput(firstName, lastName, studentId, selectedOption, email)) {
                // Registration successful, you can proceed to save the data
                // or navigate to another screen
                Toast.makeText(
                    this@RegistrationActivity,
                    "Registration Successful",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Display an error message if validation fails
                Toast.makeText(this@RegistrationActivity, "Invalid Input", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    // Function to validate user input
    private fun isValidInput(
        firstName: String,
        lastName: String,
        studentId: Int,
        selectedOption: String,
        email: String
    ): Boolean {
        // Implement your validation logic here
        // Example: Check if fields are not empty, validate email format, etc.
        return !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty()
    }
}