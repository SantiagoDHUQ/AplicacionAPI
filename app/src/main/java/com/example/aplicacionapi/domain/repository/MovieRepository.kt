package com.example.aplicacionapi.domain.repository

import com.example.aplicacionapi.domain.model.Genre
import com.example.aplicacionapi.domain.model.Movie
import com.example.aplicacionapi.domain.model.MovieDetail

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): Result<List<Movie>>
    suspend fun searchMovies(query: String, page: Int): Result<List<Movie>>
    suspend fun filterMovies(genreId: Int?, year: Int?, page: Int): Result<List<Movie>>
    suspend fun getMovieDetails(movieId: Int): Result<MovieDetail>
    suspend fun getGenres(): Result<List<Genre>>
}
