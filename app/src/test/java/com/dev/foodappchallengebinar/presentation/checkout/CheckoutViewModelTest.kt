package com.dev.foodappchallengebinar.presentation.checkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dev.foodappchallengebinar.data.repository.CartRepository
import com.dev.foodappchallengebinar.data.repository.MenuRepository
import com.dev.foodappchallengebinar.tools.MainCoroutineRule
import com.dev.foodappchallengebinar.tools.getOrAwaitValue
import com.dev.foodappchallengebinar.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CheckoutViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepository: CartRepository

    @MockK
    lateinit var menuRepository: MenuRepository

    private lateinit var viewModel: CheckoutViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { cartRepository.getCheckoutData() } returns flow { mockk() }
        viewModel = spyk(CheckoutViewModel(cartRepository, menuRepository))
    }

    @Test
    fun checkoutCart() {
        val fakeOrderResult = mockk<ResultWrapper<Boolean>>()
        every { menuRepository.createOrder(any()) } returns flow { emit(fakeOrderResult) }
        val result = viewModel.checkoutCart().getOrAwaitValue()
        assertEquals(fakeOrderResult, result)
        verify { menuRepository.createOrder(any()) }
    }

    @Test
    fun deleteAll() =
        runBlocking {
            val fakeDeleteResult = ResultWrapper.Success(true)
            every { cartRepository.deleteAll() } returns flow { emit(fakeDeleteResult) }
            val result = viewModel.deleteAll().first()
            assertEquals(fakeDeleteResult, result)
            verify { cartRepository.deleteAll() }
        }
}
