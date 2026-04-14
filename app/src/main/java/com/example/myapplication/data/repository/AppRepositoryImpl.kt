package com.example.myapplication.data.repository

import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.mapper.toDomainList
import com.example.myapplication.data.network.CatalogApi
import com.example.myapplication.domain.model.App
import com.example.myapplication.domain.repository.AppRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val catalogApi: CatalogApi
) : AppRepository {

    override suspend fun getApps(): List<App> {
        return try {
            catalogApi.getCatalog().toDomainList()
        } catch (_: Exception) {
            emptyList()
        }
    }

    override fun observeApps(): Flow<List<App>> = flow {
        emit(getApps())
    }

    override suspend fun getAppById(id: String): App? {
        return try {
            catalogApi.getAppById(id).toDomain()
        } catch (_: Exception) {
            null
        }
    }
}

