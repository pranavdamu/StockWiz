package com.example.stockwiz_prototype.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.stockwiz_prototype.R
import com.example.stockwiz_prototype.ui.loginandsignup.SignInActivity
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        auth = FirebaseAuth.getInstance()
        val userIdTextView: TextView = view.findViewById(R.id.textUserId)
        val signOutButton: Button = view.findViewById(R.id.buttonSignOut)

        // Get current user from Firebase Auth
        val user = auth.currentUser

        // Display the user's email or UID as a fallback
        val userIdentifier = user?.email ?: "User ID: ${user?.uid ?: "Not logged in"}"
        userIdTextView.text = if (user?.email != null) "Email: $userIdentifier" else "User ID: $userIdentifier"

        signOutButton.setOnClickListener {
            auth.signOut()
            navigateToSignIn()
        }

        return view
    }

    private fun navigateToSignIn() {
        val intent = Intent(activity, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
