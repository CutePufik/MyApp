package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.App

interface AppRepository {
    fun getApps(): List<App>
    fun getAppById(id: Int): App?
}

