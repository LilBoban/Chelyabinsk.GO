package com.example.chelyabinskgo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chelyabinskgo.domain.model.PlaceMock
import com.example.chelyabinskgo.domain.usecase.GetFavoritePlacesUseCase
import com.example.chelyabinskgo.domain.usecase.TogglePlaceFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FavoritePlacesUiState(
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

class FavoritePlacesViewModel(
    private val getFavoritePlacesUseCase: GetFavoritePlacesUseCase,
    private val togglePlaceFavoriteUseCase: TogglePlaceFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritePlacesUiState(isLoading = true))
    val uiState: StateFlow<FavoritePlacesUiState> = _uiState.asStateFlow()

    init {
        loadPlaces()
    }

    fun loadPlaces() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val data = getFavoritePlacesUseCase()
                val categories = data.map { it.category }.distinct()

                val currentCategory = _uiState.value.selectedCategory
                val newSelectedCategory = if (categories.contains(currentCategory)) currentCategory else categories.firstOrNull().orEmpty()

                _uiState.value = FavoritePlacesUiState(
                    isLoading = false,
                    places = data,
                    categories = categories,
                    selectedCategory = newSelectedCategory
                )
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Ошибка загрузки избранных мест")
                }
            }
        }
    }

    fun selectCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }
}