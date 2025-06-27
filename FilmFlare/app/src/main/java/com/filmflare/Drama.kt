package com.filmflare

import Cast

data class Drama(
    val cast: List<Cast>,
    val description: String,
    val duration: String,
    val genre: String,
    val poster: String,
    val rating: Double,
    val title: String,
    val year: Int
)