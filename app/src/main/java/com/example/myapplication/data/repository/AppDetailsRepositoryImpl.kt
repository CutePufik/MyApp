package com.example.myapplication.data.repository

import com.example.myapplication.data.appdetails.local.AppDetailsDao
import com.example.myapplication.data.appdetails.local.AppDetailsEntityMapper
import com.example.myapplication.data.mapper.toAppDetailsEntity
import com.example.myapplication.data.network.CatalogApi
import com.example.myapplication.domain.model.App
import com.example.myapplication.domain.model.AppDetails
import com.example.myapplication.domain.repository.AppDetailsRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext

@Singleton
class AppDetailsRepositoryImpl @Inject constructor(
    private val appDetailsDao: AppDetailsDao,
    private val catalogApi: CatalogApi,
    private val entityMapper: AppDetailsEntityMapper
) : AppDetailsRepository {

    override suspend fun getAppDetails(id: String): AppDetails {
        val entity = appDetailsDao.getAppDetails(id).first()
        if (entity != null) {
            return entityMapper.toDomain(entity)
        }
        val dto = catalogApi.getAppById(id)
        val newEntity = entityMapper.toEntity(dto, isInWishlist = false)
        withContext(Dispatchers.IO) {
            appDetailsDao.upsert(newEntity)
        }
        return entityMapper.toDomain(newEntity)
    }

    override fun observeAppDetails(id: String): Flow<AppDetails> {
        return appDetailsDao.getAppDetails(id)
            .mapNotNull { e -> e?.let { entityMapper.toDomain(it) } }
    }

    override suspend fun toggleWishlist(id: String) {
        val currentEntity = appDetailsDao.getAppDetails(id).first()
        currentEntity?.let {
            appDetailsDao.updateWishlistStatus(id, !it.isInWishlist)
        }
    }

    override suspend fun upsertFromCatalogApp(app: App) {
        val existing = appDetailsDao.getAppDetailsOnce(app.id)
        appDetailsDao.upsert(
            app.toAppDetailsEntity(isInWishlist = existing?.isInWishlist ?: false)
        )
    }
}
