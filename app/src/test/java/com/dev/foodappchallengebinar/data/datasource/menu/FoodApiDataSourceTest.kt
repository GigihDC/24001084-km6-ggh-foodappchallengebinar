package com.dev.foodappchallengebinar.data.datasource.menu

import com.dev.foodappchallengebinar.data.source.network.model.checkout.CheckoutRequestPayload
import com.dev.foodappchallengebinar.data.source.network.model.checkout.CheckoutResponse
import com.dev.foodappchallengebinar.data.source.network.model.menu.FoodResponse
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

class FoodApiDataSourceTest {
    @MockK
    lateinit var service: FoodAppApiService
    private lateinit var ds: FoodDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = FoodApiDataSource(service)
    }

    @Test
    fun getMenu() {
        runTest {
            val mockResponse = mockk<FoodResponse>(relaxed = true)
            coEvery { service.getFood(any()) } returns mockResponse
            val actualResponse = ds.getFoodDetail("listmenu")
            coVerify { service.getFood(any()) }
            assertEquals(actualResponse, mockResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockRequest = mockk<CheckoutRequestPayload>(relaxed = true)
            val mockResponse = mockk<CheckoutResponse>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val actualResponse = ds.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(actualResponse, mockResponse)
        }
    }
}
