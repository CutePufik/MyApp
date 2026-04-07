package com.example.myapplication.data.mapper

import com.example.myapplication.data.appdetails.local.AppDetailsEntity
import com.example.myapplication.data.dto.AppDto
import com.example.myapplication.domain.model.App
import com.example.myapplication.domain.model.AppDetails
import org.junit.Assert.assertEquals
import org.junit.Test

class MappersTest {

    @Test
    fun `AppDto toDomain maps all fields`() {
        val dto = AppDto("1", "Name", "Desc", "Category", "Icon")

        val domain = dto.toDomain()

        assertEquals("1", domain.id)
        assertEquals("Name", domain.name)
        assertEquals("Desc", domain.description)
        assertEquals("Category", domain.category)
        assertEquals("Icon", domain.iconUrl)
    }

    @Test
    fun `List AppDto toDomainList maps every element`() {
        val dtoList = listOf(
            AppDto("1", "A", "d1", "c1", "i1"),
            AppDto("2", "B", "d2", "c2", "i2")
        )

        val domainList = dtoList.toDomainList()

        assertEquals(2, domainList.size)
        assertEquals("1", domainList[0].id)
        assertEquals("2", domainList[1].id)
    }

    @Test
    fun `AppDetailsEntity toDomain maps wishlist flag`() {
        val entity = AppDetailsEntity("10", "N", "D", "C", "I", isInWishlist = true)

        val domain = entity.toDomain()

        assertEquals(true, domain.isInWishlist)
        assertEquals("10", domain.id)
    }

    @Test
    fun `AppDetails toEntity maps all fields`() {
        val domain = AppDetails("11", "N", "D", "C", "I", isInWishlist = false)

        val entity = domain.toEntity()

        assertEquals("11", entity.id)
        assertEquals("N", entity.name)
        assertEquals(false, entity.isInWishlist)
    }

    @Test
    fun `App toAppDetailsEntity uses provided wishlist value`() {
        val app = App("20", "Title", "Description", "Cat", "Icon")

        val entity = app.toAppDetailsEntity(isInWishlist = true)

        assertEquals("20", entity.id)
        assertEquals("Title", entity.name)
        assertEquals(true, entity.isInWishlist)
    }
}
