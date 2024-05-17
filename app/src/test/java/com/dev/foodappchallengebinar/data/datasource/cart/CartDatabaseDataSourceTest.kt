package com.dev.foodappchallengebinar.data.datasource.cart

import app.cash.turbine.test
import com.dev.foodappchallengebinar.data.source.local.database.dao.CartDao
import com.dev.foodappchallengebinar.data.source.local.database.entity.CartEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CartDatabaseDataSourceTest {
    @MockK
    lateinit var cartDao: CartDao
    private lateinit var cartDataSource: CartDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        cartDataSource = CartDatabaseDataSource(cartDao)
    }

    @Test
    fun getAllCarts() {
        val entity1 = mockk<CartEntity>()
        val entity2 = mockk<CartEntity>()
        val listEntity = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(listEntity)
            }
        every { cartDao.getAllCarts() } returns mockFlow
        runTest {
            cartDataSource.getAllCarts().test {
                val result = awaitItem()
                Assert.assertEquals(listEntity.size, result.size)
                Assert.assertEquals(entity1, result[0])
                Assert.assertEquals(entity2, result[1])
                awaitComplete()
            }
        }
    }

    @Test
    fun insertCart() {
        runTest {
            val mockEntity = mockk<CartEntity>()
            coEvery { cartDao.insertCart(any()) } returns 1
            val result = cartDataSource.insertCart(mockEntity)
            coVerify { cartDao.insertCart(any()) }
            Assert.assertEquals(1, result)
        }
    }

    @Test
    fun updateCart() {
        runTest {
            val mockEntity = mockk<CartEntity>()
            coEvery { cartDao.updateCart(any()) } returns 1
            val result = cartDataSource.updateCart(mockEntity)
            coVerify { cartDao.updateCart(any()) }
            Assert.assertEquals(1, result)
        }
    }

    @Test
    fun deleteCart() {
        runTest {
            val mockEntity = mockk<CartEntity>()
            coEvery { cartDao.deleteCart(any()) } returns 1
            val result = cartDataSource.deleteCart(mockEntity)
            coVerify { cartDao.deleteCart(any()) }
            Assert.assertEquals(1, result)
        }
    }

    @Test
    fun deleteAll() {
        runTest {
            coEvery { cartDao.deleteAll() } returns Unit
            val result = cartDataSource.deleteAll()
            coVerify { cartDao.deleteAll() }
            Assert.assertEquals(Unit, result)
        }
    }
}
