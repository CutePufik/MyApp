package com.example.myapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.model.App
import com.example.myapplication.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class AppDetailsUiState(
    val app: App? = null
)

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appRepository: AppRepository
) : ViewModel() {

    private val appId: Int = savedStateHandle.get<Int>("appId") ?: -1

    private val _uiState = MutableLiveData(AppDetailsUiState())
    val uiState: LiveData<AppDetailsUiState> = _uiState

    init {
        _uiState.value = AppDetailsUiState(app = appRepository.getAppById(appId))
    }
}

