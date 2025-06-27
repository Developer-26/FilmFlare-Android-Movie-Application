package com.filmflare

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class FilmFlareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Enable disk persistence for Firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
