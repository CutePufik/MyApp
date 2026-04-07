package com.example.myapplication.data.repository

import com.example.myapplication.data.appdetails.local.AppDetailsDao
import com.example.myapplication.data.appdetails.local.AppDetailsEntity
import com.example.myapplication.data.appdetails.local.AppDetailsEntityMapper
import com.example.myapplication.data.dto.AppDto
import com.example.myapplication.data.network.CatalogApi
import com.example.myapplication.domain.model.App
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppDetailsRepositoryImplTest {

    @Test
    fun `getAppDetails returns cached value from database`() = runBlocking {
        val entity = AppDetailsEntity("1", "Cached", "desc", "cat", "icon", true)
        val dao = FakeAppDetailsDao(initial = entity)
        val api = FakeCatalogApi()
        val repository = AppDetailsRepositoryImpl(dao, api, AppDetailsEntityMapper())

        val result = repository.getAppDetails("1")

        assertEquals("Cached", result.name)
        assertEquals(0, api.getByIdCalls)
    }

    @Test
    fun `getAppDetails fetches from network and saves when cache is empty`() = runBlocking {
        val dao = FakeAppDetailsDao(initial = null)
        val api = FakeCatalogApi(appById = AppDto("2", "Remote", "desc", "tools", "icon"))
        val repository = AppDetailsRepositoryImpl(dao, api, AppDetailsEntityMapper())

        val result = repository.getAppDetails("2")

        assertEquals("Remote", result.name)
        assertEquals(1, api.getByIdCalls)
        assertEquals("2", dao.lastUpserted?.id)
    }

    @Test
    fun `toggleWishlist inverses current wishlist value`() = runBlocking {
        val dao = FakeAppDetailsDao(
            initial = AppDetailsEntity("3", "N", "D", "C", "I", isInWishlist = false)
        )
        val repository = AppDetailsRepositoryImpl(dao, FakeCatalogApi(), AppDetailsEntityMapper())

        repository.toggleWishlist("3")

        assertEquals("3", dao.lastWishlistUpdateId)
        assertTrue(dao.lastWishlistValue == true)
    }

    @Test
    fun `upsertFromCatalogApp preserves existing wishlist flag`() = runBlocking {
        val dao = FakeAppDetailsDao(
            initial = AppDetailsEntity("9", "old", "old", "old", "old", isInWishlist = true)
        )
        val repository = AppDetailsRepositoryImpl(dao, FakeCatalogApi(), AppDetailsEntityMapper())

        repository.upsertFromCatalogApp(
            App("9", "new", "desc", "cat", "icon")
        )

        assertEquals(true, dao.lastUpserted?.isInWishlist)
        assertEquals("new", dao.lastUpserted?.name)
    }

    @Test
    fun `observeAppDetails emits mapped domain value`() = runBlocking {
        val dao = FakeAppDetailsDao(
            initial = AppDetailsEntity("11", "A", "B", "C", "D", false)
        )
        val repository = AppDetailsRepositoryImpl(dao, FakeCatalogApi(), AppDetailsEntityMapper())

        val result = repository.observeAppDetails("11").first()

        assertEquals("11", result.id)
        assertEquals("A", result.name)
    }

    private class FakeCatalogApi(
        private val appById: AppDto? = null
    ) : CatalogApi {
        var getByIdCalls: Int = 0

        override suspend fun getCatalog(): List<AppDto> = emptyList()

        override suspend fun getAppById(id: String): AppDto {
            getByIdCalls++
            return appById ?: error("No dto configured")
        }
    }

    private class FakeAppDetailsDao(initial: AppDetailsEntity?) : AppDetailsDao {
        private val state = MutableStateFlow(initial)
        var lastUpserted: AppDetailsEntity? = null
        var lastWishlistUpdateId: String? = null
        var lastWishlistValue: Boolean? = null

        override fun getAppDetails(id: String): Flow<AppDetailsEntity?> = state

        override suspend fun getAppDetailsOnce(id: String): AppDetailsEntity? = state.value

        override suspend fun upsert(entity: AppDetailsEntity) {
            lastUpserted = entity
            state.value = entity
        }

        override suspend fun updateWishlistStatus(id: String, isInWishlist: Boolean) {
            lastWishlistUpdateId = id
            lastWishlistValue = isInWishlist
            state.value = state.value?.copy(isInWishlist = isInWishlist)
        }
    }
}
