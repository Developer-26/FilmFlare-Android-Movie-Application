package com.filmflare

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Base64
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.filmflare.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.util.concurrent.Executor
import com.google.firebase.auth.EmailAuthProvider


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Set up the biometric authentication
        executor = ContextCompat.getMainExecutor(this)
        // Inside onCreate()

// Biometric Authentication Succeeded - Show saved accounts instead of signing in with Google
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // Display the saved accounts dialog
                showSavedAccountsDialog()  // Show saved accounts instead of directly triggering Google SSO
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        })


        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        binding.biometricLoginButton.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        saveAccount(email, password)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.forgotPassword.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_forgot, null)
            val userEmail = view.findViewById<EditText>(R.id.editBox)

            builder.setView(view)
            val dialog = builder.create()
            view.findViewById<Button>(R.id.btnReset).setOnClickListener{
                compareEmail(userEmail)
                dialog.dismiss()
            }
            view.findViewById<Button>(R.id.btnCancel).setOnClickListener{
                dialog.dismiss()
            }
            if(dialog.window != null){
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            // Show the dialog
            dialog.show()  // <-- Add this line to display the dialog
        }


        binding.signupRedirectText.setOnClickListener {
            val signupIntent = Intent(this, SignupActivity::class.java)
            startActivity(signupIntent)
        }

        // Google Sign-In button listener
        binding.googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }
    }

    // Outside onCreate
    private fun compareEmail(email: EditText){
        if(email.text.toString().isEmpty()){
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            return
        }
        firebaseAuth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener{task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Check Your Email", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun encryptPassword(plainText: String): String {
        return Base64.encodeToString(plainText.toByteArray(), Base64.DEFAULT)
    }

    private fun decryptPassword(encryptedText: String): String {
        return String(Base64.decode(encryptedText, Base64.DEFAULT))
    }

    private fun saveAccount(email: String, password: String?) {
        val encryptedPassword = if (password != null) encryptPassword(password) else "GOOGLE_ACCOUNT"  // Mark Google accounts
        val sharedPref = getSharedPreferences("accounts", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(email, encryptedPassword)
            apply()
        }
    }


    private fun showSavedAccountsDialog() {
        val sharedPref = getSharedPreferences("accounts", Context.MODE_PRIVATE)
        val accounts = sharedPref.all.keys.toTypedArray()

        if (accounts.isNotEmpty()) {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Select an Account")
            builder.setItems(accounts) { _, which ->
                val selectedEmail = accounts[which]
                val encryptedPassword = sharedPref.getString(selectedEmail, "")

                // Check if the account is a Google account (marked as "GOOGLE_ACCOUNT")
                if (encryptedPassword == "GOOGLE_ACCOUNT") {
                    // It's a Google account, sign in using Google
                    googleSignInForSavedAccount(selectedEmail)
                } else if (!encryptedPassword.isNullOrEmpty()) {
                    // It's a regular email/password account, decrypt the password and sign in
                    val selectedPassword = decryptPassword(encryptedPassword)
                    firebaseAuth.signInWithEmailAndPassword(selectedEmail, selectedPassword)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
            builder.show()
        } else {
            Toast.makeText(this, "No saved accounts found", Toast.LENGTH_SHORT).show()
        }
    }

    // Trigger Google sign-in specifically for saved accounts
    private fun googleSignInForSavedAccount(email: String) {
        // Check if the user is already signed in
        val currentAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (currentAccount?.email == email) {
            // User already signed in, continue
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            // Prompt Google Sign-In again
            signInWithGoogle()
        }
    }





    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        if (account == null) {
            Toast.makeText(this, "Google account is null", Toast.LENGTH_SHORT).show()
            return
        }

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        // Sign in with the Google credential directly
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { googleSignInTask ->
            if (googleSignInTask.isSuccessful) {
                // The user has signed in with Google successfully, now check if there are other methods
                val user = googleSignInTask.result?.user

                if (user != null) {
                    // Check if the user has any other linked authentication methods
                    firebaseAuth.fetchSignInMethodsForEmail(user.email!!)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val signInMethods = task.result?.signInMethods ?: emptyList()

                                if (signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
                                    // The account has an email/password sign-in method
                                    Toast.makeText(this, "Google account linked with email/password account", Toast.LENGTH_SHORT).show()
                                }

                                // Redirect to the main activity
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Failed to fetch sign-in methods: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            } else {
                // Handle the case where Google sign-in fails
                Toast.makeText(this, "Firebase authentication failed: ${googleSignInTask.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
