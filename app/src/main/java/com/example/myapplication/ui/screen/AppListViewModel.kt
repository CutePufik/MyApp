package com.example.myapplication.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.AppRepository




class AppListViewModel : ViewModel() {
    private val _uiState = MutableLiveData(AppListUiState())
    val uiState: LiveData<AppListUiState> = _uiState

    private val _showLogoClickedSnackbar = MutableLiveData(false)
    val showLogoClickedSnackbar: LiveData<Boolean> = _showLogoClickedSnackbar

    init {
        _uiState.value = AppListUiState(apps = AppRepository.apps)
    }

    fun onLogoClick() {
        _showLogoClickedSnackbar.value = true
    }

    fun onLogoSnackbarShown() {
        _showLogoClickedSnackbar.value = false
    }
}

