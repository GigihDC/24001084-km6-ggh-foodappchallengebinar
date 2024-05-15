package com.dev.foodappchallengebinar.data.datasource.category

import com.dev.foodappchallengebinar.data.source.network.model.category.CategoriesResponse
import com.dev.foodappchallengebinar.data.source.network.services.FoodAppApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FoodCategoryApiDataSourceTest {
    @MockK
    lateinit var service: FoodAppApiService
    private lateinit var ds: FoodCategoryDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = FoodCategoryApiDataSource(service)
    }

    @Test
    fun getFoodCategory() {
        runTest {
            val mockResponse = mockk<CategoriesResponse>(relaxed = true)
            coEvery { service.getCategories() } returns mockResponse
            val actualResult = ds.getFoodCategory()
            coVerify { service.getCategories() }
            assertEquals(mockResponse, actualResult)
        }
    }
}
