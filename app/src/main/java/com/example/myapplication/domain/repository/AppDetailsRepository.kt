package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.App
import com.example.myapplication.domain.model.AppDetails
import kotlinx.coroutines.flow.Flow

interface AppDetailsRepository {
    fun observeAppDetails(id: String): Flow<AppDetails>

    suspend fun getAppDetails(id: String): AppDetails

    suspend fun toggleWishlist(id: String)

    suspend fun upsertFromCatalogApp(app: App)
}
