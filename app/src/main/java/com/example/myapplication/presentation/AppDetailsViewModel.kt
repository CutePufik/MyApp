package com.example.myapplication.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.repository.AppDetailsRepository
import com.example.myapplication.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appRepository: AppRepository,
    private val appDetailsRepository: AppDetailsRepository
) : ViewModel() {

    private val appId: String = savedStateHandle.get<String>("appId").orEmpty()

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val app = appRepository.getAppById(appId)
                if (app != null) {
                    appDetailsRepository.upsertFromCatalogApp(app)
                } else {
                    _state.value = AppDetailsState.Error
                }
            } catch (_: Exception) {
                _state.value = AppDetailsState.Error
            }
        }
        observeAppDetails()
    }

    private fun observeAppDetails() {
        viewModelScope.launch {
            appDetailsRepository.observeAppDetails(appId)
                .catch { _state.value = AppDetailsState.Error }
                .collect { appDetails ->
                    _state.value = AppDetailsState.Content(
                        appDetails = appDetails,
                        descriptionCollapsed = false,
                    )
                }
        }
    }

    fun toggleWishlist() {
        viewModelScope.launch {
            appDetailsRepository.toggleWishlist(appId)
        }
    }
}
