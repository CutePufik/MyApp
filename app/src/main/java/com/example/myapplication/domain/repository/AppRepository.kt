package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.App

interface AppRepository {
    suspend fun getApps(): List<App>
    suspend fun getAppById(id: String): App?
}

