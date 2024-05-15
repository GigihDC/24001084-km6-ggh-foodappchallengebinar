package com.dev.foodappchallengebinar.presentation.detail

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dev.foodappchallengebinar.data.models.Menu
import com.dev.foodappchallengebinar.data.repository.CartRepository
import com.dev.foodappchallengebinar.tools.MainCoroutineRule
import com.dev.foodappchallengebinar.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class DetailViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepository: CartRepository

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val mockedMenu = mockk<Menu>()
        every { mockedMenu.price } returns 10.0
        val mockedBundle = mockk<Bundle>()
        every { mockedBundle.getParcelable<Menu>(any()) } returns mockedMenu
        viewModel = spyk(DetailViewModel(mockedBundle, cartRepository))
    }

    @Test
    fun add() {
        viewModel.menuCountLiveData.value = 1
        viewModel.priceLiveData.value = 10.0
        viewModel.add()
        assertEquals(2, viewModel.menuCountLiveData.value)
        assertEquals(20.0, viewModel.priceLiveData.value)
    }

    @Test
    fun minus() {
        viewModel.menuCountLiveData.value = 2
        viewModel.priceLiveData.value = 20.0
        viewModel.minus()
        assertEquals(1, viewModel.menuCountLiveData.value)
        assertEquals(10.0, viewModel.priceLiveData.value)
    }

    @Test
    fun addToCart() {
        val menu = Menu("1", "Test Menu", 10000.0, "ashbdas", "asgjb", "sagas") // Dummy menu
        viewModel.menu = menu
        viewModel.menuCountLiveData.value = 2
        val fakeResult = ResultWrapper.Success(true)
        every { cartRepository.createCart(any(), any()) } returns flow { emit(fakeResult) }
        val observer = mockk<Observer<ResultWrapper<Boolean>>>(relaxed = true)
        viewModel.addToCart().observeForever(observer)
        viewModel.addToCart()
        verify { cartRepository.createCart(menu, 2) }
        verify { observer.onChanged(fakeResult) }
    }

    @Test
    fun addToCart_is_null() {
        val observer = mockk<Observer<ResultWrapper<Boolean>>>(relaxed = true)
        viewModel.menu = null
        viewModel.addToCart().observeForever(observer)
        viewModel.addToCart()
        verify(exactly = 0) { cartRepository.createCart(any(), any()) }
        val captor = slot<ResultWrapper<Boolean>>()
        verify { observer.onChanged(capture(captor)) }
        assertTrue(captor.captured is ResultWrapper.Error)
        assertEquals(
            "Product not found",
            (captor.captured as ResultWrapper.Error).exception?.message,
        )
    }
}
