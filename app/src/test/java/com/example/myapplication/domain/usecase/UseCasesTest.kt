package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.App
import com.example.myapplication.domain.model.AppDetails
import com.example.myapplication.domain.repository.AppDetailsRepository
import com.example.myapplication.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UseCasesTest {

    @Test
    fun `GetAppsUseCase returns data from repository`() = runBlocking {
        val repository = FakeAppRepository(
            apps = listOf(App("1", "A", "D", "C", "I"))
        )
        val useCase = GetAppsUseCase(repository)

        val result = useCase()

        assertEquals(1, result.size)
        assertEquals("1", result.first().id)
    }

    @Test
    fun `GetAppByIdUseCase passes id and returns repository value`() = runBlocking {
        val repository = FakeAppRepository(
            appById = App("7", "Card", "D", "C", "I")
        )
        val useCase = GetAppByIdUseCase(repository)

        val result = useCase("7")

        assertEquals("7", repository.lastRequestedId)
        assertEquals("Card", result?.name)
    }

    @Test
    fun `GetAppDetailsUseCase returns details from repository`() = runBlocking {
        val repository = FakeAppDetailsRepository(
            details = AppDetails("3", "N", "D", "C", "I", true)
        )
        val useCase = GetAppDetailsUseCase(repository)

        val result = useCase("3")

        assertEquals("3", repository.lastDetailsRequestId)
        assertEquals(true, result.isInWishlist)
    }

    @Test
    fun `ToggleWishlistUseCase delegates call to repository`() = runBlocking {
        val repository = FakeAppDetailsRepository(details = AppDetails("1", "N", "D", "C", "I"))
        val useCase = ToggleWishlistUseCase(repository)

        useCase("55")

        assertEquals("55", repository.lastToggleId)
    }

    @Test
    fun `ObserveAppDetailsUseCase returns flow from repository`() = runBlocking {
        val expected = AppDetails("9", "Flow", "D", "C", "I", false)
        val repository = FakeAppDetailsRepository(details = expected)
        val useCase = ObserveAppDetailsUseCase(repository)

        val result = useCase("9").first()

        assertEquals("9", repository.lastObserveId)
        assertTrue(result == expected)
    }

    private class FakeAppRepository(
        private val apps: List<App> = emptyList(),
        private val appById: App? = null
    ) : AppRepository {
        var lastRequestedId: String? = null

        override suspend fun getApps(): List<App> = apps

        override fun observeApps(): Flow<List<App>> = MutableStateFlow(apps)

        override suspend fun getAppById(id: String): App? {
            lastRequestedId = id
            return appById
        }
    }

    private class FakeAppDetailsRepository(
        private val details: AppDetails
    ) : AppDetailsRepository {
        var lastDetailsRequestId: String? = null
        var lastToggleId: String? = null
        var lastObserveId: String? = null

        override fun observeAppDetails(id: String): Flow<AppDetails> {
            lastObserveId = id
            return MutableStateFlow(details)
        }

        override suspend fun getAppDetails(id: String): AppDetails {
            lastDetailsRequestId = id
            return details
        }

        override suspend fun toggleWishlist(id: String) {
            lastToggleId = id
        }

        override suspend fun upsertFromCatalogApp(app: App) = Unit
    }
}
