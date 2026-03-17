package com.example.myapplication.ui.screen

import com.example.myapplication.data.AppItem

data class AppListUiState(
    val apps: List<AppItem> = emptyList()
)