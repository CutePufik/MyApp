package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.AppDto
import com.example.myapplication.data.network.CatalogApi
import com.example.myapplication.domain.model.App
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AppRepositoryImplTest {

    @Test
    fun `getApps maps network data to domain`() = runBlocking {
        val api = FakeCatalogApi(
            catalog = listOf(
                AppDto("1", "A", "desc", "cat", "icon")
            )
        )
        val repository = AppRepositoryImpl(api)

        val result = repository.getApps()

        assertEquals(1, result.size)
        assertEquals("1", result.first().id)
        assertEquals("A", result.first().name)
    }

    @Test
    fun `getApps returns empty list when api throws`() = runBlocking {
        val api = FakeCatalogApi(catalogError = RuntimeException("boom"))
        val repository = AppRepositoryImpl(api)

        val result = repository.getApps()

        assertEquals(emptyList<App>(), result)
    }

    @Test
    fun `observeApps emits same data as getApps`() = runBlocking {
        val api = FakeCatalogApi(
            catalog = listOf(AppDto("42", "Name", "D", "Games", "u"))
        )
        val repository = AppRepositoryImpl(api)

        val result = repository.observeApps().first()

        assertEquals(1, result.size)
        assertEquals("42", result.first().id)
    }

    @Test
    fun `getAppById maps dto to domain`() = runBlocking {
        val api = FakeCatalogApi(
            appById = AppDto("7", "Card", "desc", "Tools", "icon")
        )
        val repository = AppRepositoryImpl(api)

        val result = repository.getAppById("7")

        assertEquals("7", result?.id)
        assertEquals("Card", result?.name)
    }

    @Test
    fun `getAppById returns null when api throws`() = runBlocking {
        val api = FakeCatalogApi(appByIdError = RuntimeException("not found"))
        val repository = AppRepositoryImpl(api)

        val result = repository.getAppById("missing")

        assertNull(result)
    }

    private class FakeCatalogApi(
        private val catalog: List<AppDto> = emptyList(),
        private val appById: AppDto? = null,
        private val catalogError: Exception? = null,
        private val appByIdError: Exception? = null
    ) : CatalogApi {
        override suspend fun getCatalog(): List<AppDto> {
            catalogError?.let { throw it }
            return catalog
        }

        override suspend fun getAppById(id: String): AppDto {
            appByIdError?.let { throw it }
            return appById ?: error("No app configured for id=$id")
        }
    }
}
