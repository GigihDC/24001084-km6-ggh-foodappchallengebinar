package com.dev.foodappchallengebinar.data.repository

import app.cash.turbine.test
import com.dev.foodappchallengebinar.data.datasource.category.FoodCategoryDataSource
import com.dev.foodappchallengebinar.data.source.network.model.category.CategoriesResponse
import com.dev.foodappchallengebinar.data.source.network.model.category.CategoryItemResponse
import com.dev.foodappchallengebinar.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CategoryRepositoryImplTest {
    @MockK
    lateinit var ds: FoodCategoryDataSource
    private lateinit var repo: CategoryRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = CategoryRepositoryImpl(ds)
    }

    @Test
    fun `get category loading`() {
        val c1 = CategoryItemResponse(id = "123", name = "gigih", imgUrl = "scsdbc")
        val c2 = CategoryItemResponse(id = "456", name = "andi", imgUrl = "shdcsdbc")
        val mockListCategory = listOf(c1, c2)
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { ds.getFoodCategory() } returns mockResponse
            repo.getCategory().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { ds.getFoodCategory() }
            }
        }
    }

    @Test
    fun `get category success`() {
        val c1 = CategoryItemResponse(id = "123", name = "gigih", imgUrl = "scsdbc")
        val c2 = CategoryItemResponse(id = "456", name = "andi", imgUrl = "shdcsdbc")
        val mockListCategory = listOf(c1, c2)
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { ds.getFoodCategory() } returns mockResponse
            repo.getCategory().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { ds.getFoodCategory() }
            }
        }
    }

    @Test
    fun `get category error`() {
        val mockResponse = mockk<CategoriesResponse>()
        coEvery { ds.getFoodCategory() } throws IllegalStateException("Mock Error")
        runTest {
            coEvery { ds.getFoodCategory() } returns mockResponse
            repo.getCategory().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { ds.getFoodCategory() }
            }
        }
    }

    @Test
    fun `get category empty`() {
        val mockListCategory = listOf<CategoryItemResponse>()
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { ds.getFoodCategory() } returns mockResponse
            repo.getCategory().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { ds.getFoodCategory() }
            }
        }
    }
}
