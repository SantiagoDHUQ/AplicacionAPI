package com.example.aplicacionapi.data.repository

import com.example.aplicacionapi.data.api.TmdbApiService
import com.example.aplicacionapi.data.model.GenreDto
import com.example.aplicacionapi.data.model.MovieDetailDto
import com.example.aplicacionapi.data.model.MovieDto
import com.example.aplicacionapi.domain.model.Genre
import com.example.aplicacionapi.domain.model.Movie
import com.example.aplicacionapi.domain.model.MovieDetail
import com.example.aplicacionapi.domain.repository.MovieRepository
import com.example.aplicacionapi.util.Constants
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return runCatching {
            val response = apiService.getPopularMovies(page = page)
            response.results?.map { it.toDomain() } ?: emptyList()
        }
    }

    override suspend fun searchMovies(query: String, page: Int): Result<List<Movie>> {
        return runCatching {
            val response = apiService.searchMovies(query = query, page = page)
            response.results?.map { it.toDomain() } ?: emptyList()
        }
    }

    override suspend fun filterMovies(genreId: Int?, year: Int?, page: Int): Result<List<Movie>> {
        return runCatching {
            val response = apiService.discoverMovies(
                genreId = genreId?.toString(),
                year = year,
                page = page
            )
            response.results?.map { it.toDomain() } ?: emptyList()
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetail> {
        return runCatching {
            val detailDto = apiService.getMovieDetails(movieId = movieId)
            detailDto.toDomain()
        }
    }

    override suspend fun getGenres(): Result<List<Genre>> {
        return runCatching {
            val response = apiService.getGenres()
            response.genres?.map { it.toDomain() } ?: emptyList()
        }
    }

    // Funciones de mapeo de Data a Domain
    private fun MovieDto.toDomain(): Movie {
        val year = releaseDate?.take(4) ?: "N/A"
        return Movie(
            id = id,
            title = title ?: "Sin título",
            overview = overview ?: "Sin descripción disponible.",
            posterUrl = posterPath?.let { "${Constants.IMAGE_BASE_URL}$it" },
            backdropUrl = backdropPath?.let { "${Constants.IMAGE_BASE_URL}$it" },
            releaseDate = releaseDate ?: "N/A",
            releaseYear = year,
            voteAverage = voteAverage ?: 0.0,
            voteCount = voteCount ?: 0
        )
    }

    private fun GenreDto.toDomain(): Genre {
        return Genre(id = id, name = name)
    }

    private fun MovieDetailDto.toDomain(): MovieDetail {
        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
        return MovieDetail(
            id = id,
            title = title ?: "Sin título",
            overview = overview ?: "Sin descripción disponible.",
            posterUrl = posterPath?.let { "${Constants.IMAGE_BASE_URL}$it" },
            backdropUrl = backdropPath?.let { "${Constants.ORIGINAL_IMAGE_BASE_URL}$it" },
            releaseDate = releaseDate ?: "N/A",
            voteAverage = voteAverage ?: 0.0,
            voteCount = voteCount ?: 0,
            runtimeMinutes = runtime ?: 0,
            tagline = tagline ?: "",
            status = status ?: "Desconocido",
            genres = genres?.map { it.name } ?: emptyList(),
            budgetFormatted = if (budget != null && budget > 0) currencyFormatter.format(budget) else "No disponible",
            revenueFormatted = if (revenue != null && revenue > 0) currencyFormatter.format(revenue) else "No disponible",
            originalLanguage = originalLanguage?.uppercase(Locale.ROOT) ?: "N/A"
        )
    }
}
