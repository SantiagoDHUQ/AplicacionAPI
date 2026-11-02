package com.example.aplicacionapi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionapi.domain.model.Genre
import com.example.aplicacionapi.domain.model.Movie
import com.example.aplicacionapi.domain.repository.MovieRepository
import com.example.aplicacionapi.util.NetworkObserver
import com.example.aplicacionapi.util.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
    networkObserver: NetworkObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val networkStatus: StateFlow<NetworkStatus> = networkObserver.networkStatus
        .kotlinxStateIn(viewModelScope, NetworkStatus.Available)

    private var currentPage = 1
    private var searchJob: Job? = null

    init {
        loadGenres()
        loadMovies(isInitial = true)
    }

    private fun loadGenres() {
        viewModelScope.launch {
            repository.getGenres().onSuccess { genres ->
                _uiState.update { it.copy(genres = genres) }
            }
        }
    }

    fun loadMovies(isInitial: Boolean = false) {
        val currentState = _uiState.value
        if (currentState.isLoading || currentState.isPaginatedLoading || currentState.endOfPaginationReached) {
            if (!isInitial) return
        }

        if (isInitial) {
            currentPage = 1
            _uiState.update {
                it.copy(
                    isLoading = true,
                    movies = emptyList(),
                    errorMessage = null,
                    endOfPaginationReached = false
                )
            }
        } else {
            _uiState.update { it.copy(isPaginatedLoading = true) }
        }

        viewModelScope.launch {
            val query = currentState.searchQuery.trim()
            val genreId = currentState.selectedGenre?.id
            val year = currentState.selectedYear

            val result: Result<List<Movie>> = when {
                query.isNotEmpty() -> repository.searchMovies(query = query, page = currentPage)
                genreId != null || year != null -> repository.filterMovies(genreId = genreId, year = year, page = currentPage)
                else -> repository.getPopularMovies(page = currentPage)
            }

            result.fold(
                onSuccess = { newMovies ->
                    currentPage++
                    _uiState.update { state ->
                        state.copy(
                            movies = if (isInitial) newMovies else state.movies + newMovies,
                            isLoading = false,
                            isPaginatedLoading = false,
                            endOfPaginationReached = newMovies.isEmpty()
                        )
                    }
                },
                onFailure = { throwable ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isPaginatedLoading = false,
                            errorMessage = throwable.localizedMessage ?: "Error de red al cargar las películas."
                        )
                    }
                }
            )
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500) // Debounce para evitar llamadas innecesarias a la API
            loadMovies(isInitial = true)
        }
    }

    fun onGenreSelected(genre: Genre?) {
        _uiState.update { it.copy(selectedGenre = genre) }
        loadMovies(isInitial = true)
    }

    fun onYearSelected(year: Int?) {
        _uiState.update { it.copy(selectedYear = year) }
        loadMovies(isInitial = true)
    }

    fun retry() {
        loadMovies(isInitial = true)
    }

    private fun <T> kotlinx.coroutines.flow.Flow<T>.kotlinxStateIn(
        scope: kotlinx.coroutines.CoroutineScope,
        initialValue: T
    ): StateFlow<T> {
        val flowState = MutableStateFlow(initialValue)
        scope.launch {
            collect { flowState.value = it }
        }
        return flowState.asStateFlow()
    }
}
