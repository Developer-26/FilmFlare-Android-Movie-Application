package com.filmflare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class SettingsFragment : Fragment() {

    private lateinit var darkModeSwitch: Switch
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var logoutButton: Button
    private lateinit var languageButton: Button // Button to change language

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the switch, buttons, and shared preferences
        darkModeSwitch = view.findViewById(R.id.dark_mode_switch)
        logoutButton = view.findViewById(R.id.logout_button)
        languageButton = view.findViewById(R.id.language_button) // Language change button
        sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // Initialize Firebase Auth and GoogleSignInClient
        auth = FirebaseAuth.getInstance()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.DEFAULT_SIGN_IN)

        // Set up dark mode switch
        val isDarkMode = sharedPreferences.getBoolean("isDarkMode", false)
        darkModeSwitch.isChecked = isDarkMode
        setAppTheme(view, isDarkMode)

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("isDarkMode", isChecked).apply()
            activity?.recreate()
        }

        // Handle Logout button
        logoutButton.setOnClickListener {
            auth.signOut()
            googleSignInClient.signOut().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to log out", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Handle language change button
        languageButton.setOnClickListener {
            showLanguageDialog()
        }
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("English", "Afrikaans", "Zulu")
        val selectedLanguage = sharedPreferences.getString("appLanguage", "en")

        val currentLanguageIndex = when (selectedLanguage) {
            "en" -> 0
            "af" -> 1
            "zu" -> 2
            else -> 0
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Select Language")
            .setSingleChoiceItems(languages, currentLanguageIndex) { dialog, which ->
                when (which) {
                    0 -> setLocale("en") // English
                    1 -> setLocale("af") // Afrikaans
                    2 -> setLocale("zu") // Zulu
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)

        // Update the resources and store the chosen language in SharedPreferences
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)
        sharedPreferences.edit().putString("appLanguage", languageCode).apply()

        // Recreate activity to apply language change immediately
        activity?.recreate()
    }

    private fun setAppTheme(view: View, isDarkMode: Boolean) {
        if (isDarkMode) {
            view.setBackgroundColor(resources.getColor(R.color.dark_background_color))
        } else {
            view.setBackgroundColor(resources.getColor(R.color.light_background_color))
        }
    }
}
