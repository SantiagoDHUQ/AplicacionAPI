package com.example.aplicacionapi.data.api

import com.example.aplicacionapi.data.model.GenreResponseDto
import com.example.aplicacionapi.data.model.MovieDetailDto
import com.example.aplicacionapi.data.model.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    /**
     * Endpoint 1: Obtiene las películas populares con soporte de paginación.
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "es-ES"
    ): MovieResponseDto

    /**
     * Endpoint 2: Busca películas por texto ingresado por el usuario.
     */
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "es-ES"
    ): MovieResponseDto

    /**
     * Endpoint 3: Filtra películas mediante 2 campos (Género y Año de estreno).
     */
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("with_genres") genreId: String? = null,
        @Query("primary_release_year") year: Int? = null,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "es-ES"
    ): MovieResponseDto

    /**
     * Endpoint 4: Obtiene el detalle completo de una película por su ID.
     */
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "es-ES"
    ): MovieDetailDto

    /**
     * Endpoint 5 (Auxiliar): Obtiene la lista oficial de géneros cinematográficos.
     */
    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("language") language: String = "es-ES"
    ): GenreResponseDto
}
