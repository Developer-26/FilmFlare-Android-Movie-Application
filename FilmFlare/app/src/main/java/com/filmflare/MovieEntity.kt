package com.filmflare

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val poster: String,
    val description: String,
    val rating: Float,
    val duration: String,
    val year: Int,
    val genre: String,
    val castNamesJson: String,   // Store cast names as JSON
    val castImagesJson: String   // Store cast images as JSON
) {
    // Helper functions to deserialize JSON back into list of strings
    fun getCastNames(): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(castNamesJson, type) ?: emptyList()
    }

    fun getCastImages(): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(castImagesJson, type) ?: emptyList()
    }
}
