package com.example.myapplication.presentation

import com.example.myapplication.domain.model.AppDetails

sealed interface AppDetailsState {
    data object Loading : AppDetailsState
    data object Error : AppDetailsState
    data class Content(
        val appDetails: AppDetails,
        val descriptionCollapsed: Boolean = false
    ) : AppDetailsState
}