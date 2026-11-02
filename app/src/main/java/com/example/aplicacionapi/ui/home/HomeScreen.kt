package com.example.aplicacionapi.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionapi.ui.components.ConnectivityStatusBanner
import com.example.aplicacionapi.ui.components.ErrorState
import com.example.aplicacionapi.ui.components.MovieItem
import com.example.aplicacionapi.ui.components.SearchAndFilterBar
import com.example.aplicacionapi.ui.theme.DarkBackground
import com.example.aplicacionapi.ui.theme.DarkSurface
import com.example.aplicacionapi.ui.theme.OfflineRed
import com.example.aplicacionapi.ui.theme.OnlineGreen
import com.example.aplicacionapi.ui.theme.PrimaryRed
import com.example.aplicacionapi.ui.theme.TextPrimary
import com.example.aplicacionapi.util.NetworkStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onMovieClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val networkStatus by viewModel.networkStatus.collectAsState()

    val listState = rememberLazyListState()

    // Detectar cuando el usuario llega al final de la lista para scroll infinito (Paginación)
    val shouldLoadMore = remember {
        derivedStateOf {
            val totalItems = listState.layoutInfo.totalItemsCount
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            totalItems > 0 && lastVisibleItem >= totalItems - 3
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !uiState.isLoading && !uiState.isPaginatedLoading) {
            viewModel.loadMovies(isInitial = false)
        }
    }

    Scaffold(
        topBar = {
            Column {
                ConnectivityStatusBanner(networkStatus = networkStatus)
                TopAppBar(
                    title = {
                        Text(
                            text = "🎬 Películas REST API",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    },
                    actions = {
                        // Indicador visual continuo de estado de conexión (Online / Offline)
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (networkStatus == NetworkStatus.Available) OnlineGreen.copy(alpha = 0.2f) else OfflineRed.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, if (networkStatus == NetworkStatus.Available) OnlineGreen else OfflineRed),
                            modifier = Modifier.padding(end = 12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(
                                            color = if (networkStatus == NetworkStatus.Available) OnlineGreen else OfflineRed,
                                            shape = CircleShape
                                        )
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (networkStatus == NetworkStatus.Available) "Online" else "Offline",
                                    color = if (networkStatus == NetworkStatus.Available) OnlineGreen else OfflineRed,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkSurface)
                )
            }
        },
        containerColor = DarkBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Barra de Búsqueda y Filtrado (2 campos)
            SearchAndFilterBar(
                query = uiState.searchQuery,
                onQueryChange = viewModel::onSearchQueryChanged,
                genres = uiState.genres,
                selectedGenre = uiState.selectedGenre,
                onGenreSelected = viewModel::onGenreSelected,
                selectedYear = uiState.selectedYear,
                onYearSelected = viewModel::onYearSelected
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = PrimaryRed
                        )
                    }

                    uiState.errorMessage != null && uiState.movies.isEmpty() -> {
                        ErrorState(
                            message = uiState.errorMessage ?: "Error desconocido",
                            onRetry = viewModel::retry
                        )
                    }

                    uiState.movies.isEmpty() -> {
                        Text(
                            text = "No se encontraron películas para los criterios de búsqueda.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }

                    else -> {
                        LazyColumn(
                            state = listState,
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(
                                items = uiState.movies,
                                key = { movie -> movie.id }
                            ) { movie ->
                                MovieItem(
                                    movie = movie,
                                    onClick = { onMovieClick(movie.id) }
                                )
                            }

                            // Indicador de Carga para Scroll Infinito
                            if (uiState.isPaginatedLoading) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(color = PrimaryRed)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
