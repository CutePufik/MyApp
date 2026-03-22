package com.example.myapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(AppListUiState())
    val uiState: LiveData<AppListUiState> = _uiState

    private val _showLogoClickedSnackbar = MutableLiveData(false)
    val showLogoClickedSnackbar: LiveData<Boolean> = _showLogoClickedSnackbar

    init {
        _uiState.value = AppListUiState(apps = appRepository.getApps())
    }

    fun onLogoClick() {
        _showLogoClickedSnackbar.value = true
    }

    fun onLogoSnackbarShown() {
        _showLogoClickedSnackbar.value = false
    }
}

