package com.filmflare

import Cast

data class Popular(
    val cast: List<Cast> = listOf(),  // Default to an empty list
    val description: String = "",     // Default to an empty string
    val duration: String = "",        // Default to an empty string
    val genre: String = "",           // Default to an empty string
    val poster: String = "",          // Default to an empty string
    val rating: Double = 0.0,         // Default to 0.0
    val title: String = "",           // Default to an empty string
    val year: Int = 0                 // Default to 0 (year not specified)
)
