package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.App
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getApps(): List<App>

    fun observeApps(): Flow<List<App>>

    suspend fun getAppById(id: String): App?
}

