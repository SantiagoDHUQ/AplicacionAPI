package com.example.aplicacionapi.ui.home

import com.example.aplicacionapi.domain.model.Genre
import com.example.aplicacionapi.domain.model.Movie

data class HomeUiState(
    val movies: List<Movie> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val searchQuery: String = "",
    val selectedGenre: Genre? = null,
    val selectedYear: Int? = null,
    val isLoading: Boolean = false,
    val isPaginatedLoading: Boolean = false,
    val endOfPaginationReached: Boolean = false,
    val errorMessage: String? = null
)
