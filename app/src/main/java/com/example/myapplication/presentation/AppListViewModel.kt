package com.example.myapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.repository.AppRepository

class AppListViewModel : ViewModel() {
    private val appRepository: AppRepository = AppRepositoryProvider.provide()

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

