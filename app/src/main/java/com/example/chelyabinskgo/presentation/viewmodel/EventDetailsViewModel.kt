package com.example.chelyabinskgo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chelyabinskgo.data.repository.EventsRepository
import com.example.chelyabinskgo.domain.model.EventMock
import com.example.chelyabinskgo.domain.usecase.ToggleEventFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventDetailsViewModel(
    private val toggleUseCase: ToggleEventFavoriteUseCase,
    private val repository: EventsRepository
) : ViewModel() {

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun init(event: EventMock) {
        _isFavorite.value = event.isFavorite

        viewModelScope.launch {
            val actualState = repository.isFavorite(event.id)
            _isFavorite.value = actualState
        }
    }

    fun toggleFavorite(event: EventMock) {
        viewModelScope.launch {
            toggleUseCase(event.copy(isFavorite = _isFavorite.value))
            _isFavorite.value = !_isFavorite.value
        }
    }
}