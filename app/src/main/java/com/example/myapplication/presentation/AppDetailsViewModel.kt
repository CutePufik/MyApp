package com.example.myapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.App
import com.example.myapplication.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

data class AppDetailsUiState(
    val app: App? = null
)

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appRepository: AppRepository
) : ViewModel() {

    private val appId: String = savedStateHandle.get<String>("appId").orEmpty()

    private val _uiState = MutableLiveData(AppDetailsUiState())
    val uiState: LiveData<AppDetailsUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = AppDetailsUiState(app = appRepository.getAppById(appId))
        }
    }
}

