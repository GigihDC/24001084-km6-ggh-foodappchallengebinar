package com.dev.foodappchallengebinar.data.repository

import app.cash.turbine.test
import com.dev.foodappchallengebinar.data.datasource.cart.CartDataSource
import com.dev.foodappchallengebinar.data.models.Cart
import com.dev.foodappchallengebinar.data.models.Menu
import com.dev.foodappchallengebinar.data.source.local.database.entity.CartEntity
import com.dev.foodappchallengebinar.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CartRepositoryImplTest {
    @MockK
    lateinit var ds: CartDataSource
    private lateinit var repo: CartRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = CartRepositoryImpl(ds)
    }

    @Test
    fun getUserCartData_success() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "hfvbksdj",
                menuName = "kshadhkwf",
                menuImgUrl = "dskvbkhdjs",
                menuPrice = 10000.0,
                itemQuantity = 1,
                itemNotes = "shkdhs",
            )
        val entity2 =
            CartEntity(
                id = 2,
                menuId = "fcfgxcj",
                menuName = "ashsvch",
                menuImgUrl = "ashsg",
                menuPrice = 10000.0,
                itemQuantity = 3,
                itemNotes = "shdcghsdcvgsdc",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow = flow { emit(mockList) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockList.size, actualData.payload?.first?.size)
                Assert.assertEquals(40000.0, actualData.payload?.second)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getUserCartData_error() {
        every { ds.getAllCarts() } returns flow { throw Exception("Some error occurred") }
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Error)
                coVerify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getUserCartData_loading() {
        every { ds.getAllCarts() } returns flow { delay(2000) }
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(100)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Loading)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getUserCartData_empty() {
        val mockFlow = flow<List<CartEntity>> { emit(emptyList()) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Empty)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutData_success() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "hfvbksdj",
                menuName = "kshadhkwf",
                menuImgUrl = "dskvbkhdjs",
                menuPrice = 10000.0,
                itemQuantity = 1,
                itemNotes = "shkdhs",
            )
        val entity2 =
            CartEntity(
                id = 2,
                menuId = "fcfgxcj",
                menuName = "ashsvch",
                menuImgUrl = "ashsg",
                menuPrice = 10000.0,
                itemQuantity = 3,
                itemNotes = "shdcghsdcvgsdc",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow = flow { emit(mockList) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockList.size, actualData.payload?.first?.size)
                assertEquals(mockList.size, actualData.payload?.second?.size)
                assertEquals(40000.0, actualData.payload?.third)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutData_loading() {
        every { ds.getAllCarts() } returns flow { delay(2000) }
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Loading)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutData_error() {
        every { ds.getAllCarts() } returns flow { throw IllegalStateException("Some error occurred") }
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Error)
                coVerify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutData_empty() {
        val mockFlow = flow<List<CartEntity>> { emit(emptyList()) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Empty)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun createCart_success() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "123"
        coEvery { ds.insertCart(any()) } returns 1
        runTest {
            repo.createCart(mockMenu, 3, "afawfawf")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2201)
                    val actualData = expectMostRecentItem()
                    Assert.assertTrue(actualData is ResultWrapper.Success)
                    assertEquals(true, actualData.payload)
                    coVerify { ds.insertCart(any()) }
                }
        }
    }

    @Test
    fun createCart_success_notes_null() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "123"
        coEvery { ds.insertCart(any()) } returns 1
        runTest {
            repo.createCart(mockMenu, 3, null)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2201)
                    val actualData = expectMostRecentItem()
                    Assert.assertTrue(actualData is ResultWrapper.Success)
                    assertEquals(true, actualData.payload)
                    coVerify(exactly = 1) { ds.insertCart(any()) }
                }
        }
    }

    @Test
    fun createCart_loading() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "123"
        coEvery { ds.insertCart(any()) } returns 1
        runTest {
            repo.createCart(mockMenu, 3, "afawfawf")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(110)
                    val actualData = expectMostRecentItem()
                    Assert.assertTrue(actualData is ResultWrapper.Loading)
                    coVerify { ds.insertCart(any()) }
                }
        }
    }

    @Test
    fun createCart_error_processing() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns null
        runTest {
            repo.createCart(mockMenu, 3, "afawfawf")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(100)
                    val actualData = expectMostRecentItem()
                    Assert.assertTrue(actualData is ResultWrapper.Error)
                    coVerify(exactly = 0) { ds.insertCart(any()) }
                }
        }
    }

    @Test
    fun createCart_error_no_product_id() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns null // Simulating scenario when product id is null
        runTest {
            repo.createCart(mockMenu, 3, "afawfawf")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(100)
                    val actualData = expectMostRecentItem()
                    Assert.assertTrue(actualData is ResultWrapper.Error)
                    coVerify(exactly = 0) { ds.insertCart(any()) }
                }
        }
    }

    @Test
    fun decreaseCart_when_quantity_more_than_0() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 10000.0,
                itemQuantity = 3,
                itemNotes = "awfwafawfawfafwafa",
            )
        coEvery { ds.deleteCart(any()) } returns 1
        coEvery { ds.updateCart(any()) } returns 1
        runTest {
            repo.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 0) { ds.deleteCart(any()) }
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun decreaseCart_when_quantity_less_than_1() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 10000.0,
                itemQuantity = 0,
                itemNotes = "awfwafawfawfafwafa",
            )
        coEvery { ds.deleteCart(any()) } returns 1
        runTest {
            repo.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Success)
                coVerify(exactly = 1) { ds.deleteCart(any()) }
                coVerify(exactly = 0) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun increaseCart() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 10000.0,
                itemQuantity = 3,
                itemNotes = "awfwafawfawfafwafa",
            )
        coEvery { ds.updateCart(any()) } returns 1
        runTest {
            repo.increaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Success)
                coVerify(exactly = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun setCartNotes() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 10000.0,
                itemQuantity = 3,
                itemNotes = "awfwafawfawfafwafa",
            )
        coEvery { ds.updateCart(any()) } returns 1
        runTest {
            repo.setCartNotes(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun deleteCart() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "afwwfawf",
                menuName = "awfawf",
                menuImgUrl = "awfawfafawf",
                menuPrice = 10000.0,
                itemQuantity = 3,
                itemNotes = "awfwafawfawfafwafa",
            )
        coEvery { ds.deleteCart(any()) } returns 1
        runTest {
            repo.deleteCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Success)
                coVerify(exactly = 1) { ds.deleteCart(any()) }
            }
        }
    }

    @Test
    fun deleteAll() {
        coEvery { ds.deleteAll() } just runs
        runTest {
            repo.deleteAll().map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                Assert.assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify { ds.deleteAll() }
            }
        }
    }
}
