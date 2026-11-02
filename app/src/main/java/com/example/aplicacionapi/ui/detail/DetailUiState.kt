package com.example.aplicacionapi.ui.detail

import com.example.aplicacionapi.domain.model.MovieDetail

data class DetailUiState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
