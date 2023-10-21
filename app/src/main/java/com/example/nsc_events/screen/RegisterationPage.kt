package com.example.nsc_events.screen

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.nsc_events.R

class RegistrationActivity : AppCompatActivity() {
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var studentIdEditText: EditText
    private lateinit var facultyRadioButton: RadioButton
    private lateinit var studentRadioButton: RadioButton
    private lateinit var emailEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Initialize views
        firstNameEditText = findViewById(R.id.et_first_name)
        lastNameEditText = findViewById(R.id.et_last_name)
        studentIdEditText = findViewById(R.id.et_student_id)
        facultyRadioButton = findViewById(R.id.rb_faculty)
        studentRadioButton = findViewById(R.id.rb_student)
        emailEditText = findViewById(R.id.et_email)
        registerButton = findViewById(R.id.btn_register)

        // Set click listener for register button
        registerButton.setOnClickListener {
            // Validate form fields
            if (validateFields()) {
                // Perform registration logic here
                val firstName = firstNameEditText.text.toString()
                val lastName = lastNameEditText.text.toString()
                val studentId = studentIdEditText.text.toString()
                val isFaculty = facultyRadioButton.isChecked
                val email = emailEditText.text.toString()

                // Perform registration logic with the input values
                // ...

                // Show success message or navigate to the next screen
                // ...
            }
        }
    }

    private fun validateFields(): Boolean {
        // Perform field validation here
        // Return true if all fields are valid, otherwise false
        // ...
    }
}