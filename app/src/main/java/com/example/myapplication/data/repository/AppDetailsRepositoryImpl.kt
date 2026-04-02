package com.example.myapplication.data.repository

import com.example.myapplication.data.local.AppDetailsDao
import com.example.myapplication.data.mapper.toAppDetailsEntity
import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.domain.model.App
import com.example.myapplication.domain.model.AppDetails
import com.example.myapplication.domain.repository.AppDetailsRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapNotNull

@Singleton
class AppDetailsRepositoryImpl @Inject constructor(
    private val appDetailsDao: AppDetailsDao
) : AppDetailsRepository {

    override fun observeAppDetails(id: String): Flow<AppDetails> {
        return appDetailsDao.getAppDetails(id)
            .mapNotNull { entity -> entity?.toDomain() }
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
