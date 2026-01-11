package com.example.chelyabinskgo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chelyabinskgo.domain.model.EventMock
import com.example.chelyabinskgo.domain.usecase.GetFavoriteEventsUseCase
import com.example.chelyabinskgo.domain.usecase.ToggleEventFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FavoriteEventsUiState(
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

class FavoriteEventsViewModel(
    private val getFavoriteEventsUseCase: GetFavoriteEventsUseCase,
    private val toggleEventFavoriteUseCase: ToggleEventFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteEventsUiState(isLoading = true))
    val uiState: StateFlow<FavoriteEventsUiState> = _uiState.asStateFlow()

    init {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val data = getFavoriteEventsUseCase()
                val categories = data.map { it.category }.distinct()

                val currentCategory = _uiState.value.selectedCategory
                val newSelectedCategory = if (categories.contains(currentCategory)) currentCategory else categories.firstOrNull().orEmpty()

                _uiState.value = FavoriteEventsUiState(
                    isLoading = false,
                    events = data,
                    categories = categories,
                    selectedCategory = newSelectedCategory
                )
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Ошибка загрузки избранного")
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
            loadEvents()
        }
    }
}