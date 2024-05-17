package com.dev.foodappchallengebinar.data.repository

import app.cash.turbine.test
import com.dev.foodappchallengebinar.data.datasource.menu.FoodDataSource
import com.dev.foodappchallengebinar.data.models.Cart
import com.dev.foodappchallengebinar.data.source.network.model.checkout.CheckoutResponse
import com.dev.foodappchallengebinar.data.source.network.model.menu.FoodItemResponse
import com.dev.foodappchallengebinar.data.source.network.model.menu.FoodResponse
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
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MenuRepositoryImplTest {
    @MockK
    lateinit var ds: FoodDataSource
    private lateinit var repo: MenuRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = MenuRepositoryImpl(ds)
    }

    @Test
    fun `get menu loading`() {
        val c1 =
            FoodItemResponse(
                id = "agsda",
                imgUrl = "aghsdvjasghd",
                price = 1000.0,
                name = "sahd",
                desc = "sgvs",
                address = "asdasbd",
            )
        val c2 =
            FoodItemResponse(
                id = "agsda",
                imgUrl = "aghsdvjasghd",
                price = 1000.0,
                name = "sahd",
                desc = "sgvs",
                address = "asdasbd",
            )
        val mockMenuList = listOf(c1, c2)
        val mockResponse = mockk<FoodResponse>()
        coEvery { mockResponse.data } returns mockMenuList
        runTest {
            coEvery { ds.getFoodDetail() } returns mockResponse
            repo.getMenu().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Loading)
                coVerify { ds.getFoodDetail() }
            }
        }
    }

    @Test
    fun `get menu success`() {
        val c1 =
            FoodItemResponse(
                id = "agsda",
                imgUrl = "aghsdvjasghd",
                price = 1000.0,
                name = "sahd",
                desc = "sgvs",
                address = "asdasbd",
            )
        val c2 =
            FoodItemResponse(
                id = "agsda",
                imgUrl = "aghsdvjasghd",
                price = 1000.0,
                name = "sahd",
                desc = "sgvs",
                address = "asdasbd",
            )
        val mockMenuList = listOf(c1, c2)
        val mockResponse = mockk<FoodResponse>()
        coEvery { mockResponse.data } returns mockMenuList
        runTest {
            coEvery { ds.getFoodDetail() } returns mockResponse
            repo.getMenu().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Success)
                coVerify { ds.getFoodDetail() }
            }
        }
    }

    @Test
    fun `get menu error`() {
        val mockResponse = mockk<FoodResponse>()
        coEvery { ds.getFoodDetail() } throws IllegalStateException("Mock Error")
        runTest {
            coEvery { ds.getFoodDetail() } returns mockResponse
            repo.getMenu().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Error)
                coVerify { ds.getFoodDetail() }
            }
        }
    }

    @Test
    fun `get menu empty`() {
        val mockListCategory = listOf<FoodItemResponse>()
        val mockResponse = mockk<FoodResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { ds.getFoodDetail() } returns mockResponse
            repo.getMenu().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Empty)
                coVerify { ds.getFoodDetail() }
            }
        }
    }

    @Test
    fun `createOrder loading`() {
        val c1 =
            Cart(
                id = 1,
                menuId = "shadbas",
                menuPrice = 1000.0,
                menuName = "sahd",
                menuImgUrl = "sgvs",
                itemQuantity = 1,
                itemNotes = "sadasb",
            )
        val c2 =
            Cart(
                id = 2,
                menuId = "shadbas",
                menuPrice = 1000.0,
                menuName = "sahd",
                menuImgUrl = "sgvs",
                itemQuantity = 4,
                itemNotes = "sadasb",
            )
        val mockCartList = listOf(c1, c2)
        runTest {
            coEvery { ds.createOrder(any()) } coAnswers {
                delay(100)
                mockk()
            }
            repo.createOrder(mockCartList).map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Loading)
                coVerify { ds.createOrder(any()) }
            }
        }
    }

    @Test
    fun `createOrder success`() {
        val c1 =
            Cart(
                id = 1,
                menuId = "shadbas",
                menuPrice = 1000.0,
                menuName = "sahd",
                menuImgUrl = "sgvs",
                itemQuantity = 1,
                itemNotes = "sadasb",
            )
        val c2 =
            Cart(
                id = 2,
                menuId = "shadbas",
                menuPrice = 1000.0,
                menuName = "sahd",
                menuImgUrl = "sgvs",
                itemQuantity = 4,
                itemNotes = "sadasb",
            )
        val mockCartList = listOf(c1, c2)
        val mockResponse =
            mockk<CheckoutResponse> {
                every { status } returns true
            }
        runTest {
            coEvery { ds.createOrder(any()) } returns mockResponse
            repo.createOrder(mockCartList).test {
                val loading = awaitItem()
                Assert.assertTrue(loading is ResultWrapper.Loading)
                val success = awaitItem()
                Assert.assertTrue(success is ResultWrapper.Success)
                awaitComplete()
                coVerify(exactly = 1) { ds.createOrder(any()) }
            }
        }
    }

    @Test
    fun `createOrder error`() {
        val c1 =
            Cart(
                id = 1,
                menuId = "shadbas",
                menuPrice = 1000.0,
                menuName = "sahd",
                menuImgUrl = "sgvs",
                itemQuantity = 1,
                itemNotes = "sadasb",
            )
        val c2 =
            Cart(
                id = 2,
                menuId = "shadbas",
                menuPrice = 1000.0,
                menuName = "sahd",
                menuImgUrl = "sgvs",
                itemQuantity = 4,
                itemNotes = "sadasb",
            )
        val mockCartList = listOf(c1, c2)
        runTest {
            coEvery { ds.createOrder(any()) } throws IllegalStateException("Mock Error")
            repo.createOrder(mockCartList).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Error)
                coVerify { ds.createOrder(any()) }
            }
        }
    }
}
