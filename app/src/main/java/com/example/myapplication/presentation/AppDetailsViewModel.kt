package com.example.myapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.model.App
import com.example.myapplication.domain.repository.AppRepository

data class AppDetailsUiState(
    val app: App? = null
)

class AppDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val appId: Int = savedStateHandle.get<Int>("appId") ?: -1
    private val appRepository: AppRepository = AppRepositoryProvider.provide()

    private val _uiState = MutableLiveData(AppDetailsUiState())
    val uiState: LiveData<AppDetailsUiState> = _uiState

    init {
        _uiState.value = AppDetailsUiState(app = appRepository.getAppById(appId))
    }
}

