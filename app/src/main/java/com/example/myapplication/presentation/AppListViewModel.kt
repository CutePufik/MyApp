package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppListUiState())
    val uiState: StateFlow<AppListUiState> = _uiState.asStateFlow()

    private val _logoClickEvents = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val logoClickEvents: SharedFlow<Unit> = _logoClickEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            appRepository.observeApps()
                .catch { _uiState.value = AppListUiState(apps = emptyList()) }
                .collect { apps ->
                    _uiState.value = AppListUiState(apps = apps)
                }
        }
    }

    fun onLogoClick() {
        _logoClickEvents.tryEmit(Unit)
    }
}
