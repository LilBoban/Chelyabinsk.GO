package com.example.chelyabinskgo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chelyabinskgo.domain.model.EventMock
import com.example.chelyabinskgo.domain.usecase.GetEventsUseCase
import com.example.chelyabinskgo.domain.usecase.ToggleEventFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EventsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val events: List<EventMock> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String = ""
) {
    val filteredEvents: List<EventMock>
        get() = if (selectedCategory.isBlank()) {
            events
        } else {
            events.filter { it.category == selectedCategory }
        }
}

class EventsViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val toggleEventFavoriteUseCase: ToggleEventFavoriteUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(EventsUiState(isLoading = true))
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    init {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val data = getEventsUseCase()
                val categories = data.map { it.category }.distinct()
                _uiState.value = EventsUiState(
                    isLoading = false,
                    events = data,
                    categories = categories,
                    selectedCategory = categories.firstOrNull().orEmpty()
                )
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Failed to load events")
                }
            }
        }
    }

    fun selectCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun onFavoriteClick(event: EventMock) {
        viewModelScope.launch {
            toggleEventFavoriteUseCase(event)

            val updatedEvents = _uiState.value.events.map {
                if (it.id == event.id) it.copy(isFavorite = !it.isFavorite) else it
            }

            _uiState.update {
                it.copy(events = updatedEvents)
            }
        }
    }
}
