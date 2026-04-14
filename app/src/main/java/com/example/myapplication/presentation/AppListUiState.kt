package com.example.myapplication.presentation

import com.example.myapplication.domain.model.App

data class AppListUiState(
    val apps: List<App> = emptyList()
)

