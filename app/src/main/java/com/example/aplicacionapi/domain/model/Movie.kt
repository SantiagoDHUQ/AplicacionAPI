package com.example.aplicacionapi.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String,
    val releaseYear: String,
    val voteAverage: Double,
    val voteCount: Int
)
