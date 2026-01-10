package com.example.chelyabinskgo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chelyabinskgo.domain.model.PlaceMock
import com.example.chelyabinskgo.domain.usecase.GetPlacesUseCase
import com.example.chelyabinskgo.domain.usecase.TogglePlaceFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlacesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val places: List<PlaceMock> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String = ""
) {
    val filteredPlaces: List<PlaceMock>
        get() = if (selectedCategory.isBlank()) {
            places
        } else {
            places.filter { it.category == selectedCategory }
        }
}

class PlacesViewModel(
    private val getPlacesUseCase: GetPlacesUseCase,
    private val togglePlaceFavoriteUseCase: TogglePlaceFavoriteUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlacesUiState(isLoading = true))
    val uiState: StateFlow<PlacesUiState> = _uiState.asStateFlow()

    init {
        loadPlaces()
    }

    fun loadPlaces() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val data = getPlacesUseCase()
                val categories = data.map { it.category }.distinct()
                _uiState.value = PlacesUiState(
                    isLoading = false,
                    places = data,
                    categories = categories,
                    selectedCategory = categories.firstOrNull().orEmpty()
                )
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Failed to load places")
                }
            }
        }
    }

    fun selectCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun onFavoriteClick(place: PlaceMock) {
        viewModelScope.launch {
            togglePlaceFavoriteUseCase(place)

            val updatedPlaces = _uiState.value.places.map {
                if (it.id == place.id) it.copy(isFavorite = !it.isFavorite) else it
            }
            _uiState.update { it.copy(places = updatedPlaces) }
        }
    }
}
