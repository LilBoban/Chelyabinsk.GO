package com.example.chelyabinskgo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chelyabinskgo.data.repository.PlacesRepository
import com.example.chelyabinskgo.domain.model.PlaceMock
import com.example.chelyabinskgo.domain.usecase.TogglePlaceFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaceDetailsViewModel(
    private val toggleUseCase: TogglePlaceFavoriteUseCase,
    private val repository: PlacesRepository
) : ViewModel() {

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun init(place: PlaceMock) {
        _isFavorite.value = place.isFavorite

        viewModelScope.launch {
            val actualState = repository.isFavorite(place.id)
            _isFavorite.value = actualState
        }
    }

    fun toggleFavorite(place: PlaceMock) {
        viewModelScope.launch {
            toggleUseCase(place.copy(isFavorite = _isFavorite.value))
            _isFavorite.value = !_isFavorite.value
        }
    }
}