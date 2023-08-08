package com.example.project_2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class Email : Fragment() {

    private lateinit var editTextRecipient: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_email, container, false)

        editTextRecipient = view.findViewById(R.id.emailText)
        val btnSendEmail: Button = view.findViewById(R.id.btnSendEmail)
        btnSendEmail.setOnClickListener { sendEmail() }

        // Obtain the shared preferences from the fragment's context
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Load the last used email address from SharedPreferences
        val lastUsedEmail = sharedPreferences.getString("lastUsedEmail", "")
        editTextRecipient.setText(lastUsedEmail)

        return view
    }

    private fun sendEmail() {
        val recipient = editTextRecipient.text.toString().trim()

        // Save the entered email address to shared preferences
        sharedPreferences.edit().putString("lastUsedEmail", recipient).apply()

        // Replace the following placeholders with the actual location information
        val latitude = 37.7749
        val longitude = -122.4194
        val address = "San Francisco, CA"

        val subject = "Location Details"
        val message = "Latitude: $latitude\nLongitude: $longitude\nAddress: $address"

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, message)

        startActivity(Intent.createChooser(emailIntent, "Send Email"))
    }
}
