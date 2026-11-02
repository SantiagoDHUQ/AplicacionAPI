package com.example.aplicacionapi.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val runtimeMinutes: Int,
    val tagline: String,
    val status: String,
    val genres: List<String>,
    val budgetFormatted: String,
    val revenueFormatted: String,
    val originalLanguage: String
)
