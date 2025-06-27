package com.filmflare

data class Movies(
    val action: List<Action>,
    val crime: List<Crime>,
    val drama: List<Drama>,
    val popular: List<Popular>,
    val romance: List<Romance>,
    val science_fiction: List<ScienceFiction>
)