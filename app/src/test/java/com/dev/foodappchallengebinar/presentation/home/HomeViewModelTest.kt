package com.dev.foodappchallengebinar.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dev.foodappchallengebinar.data.models.User
import com.dev.foodappchallengebinar.data.repository.CategoryRepository
import com.dev.foodappchallengebinar.data.repository.MenuRepository
import com.dev.foodappchallengebinar.data.repository.UserRepository
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var categoryRepository: CategoryRepository

    @MockK
    lateinit var menuRepository: MenuRepository

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(HomeViewModel(categoryRepository, menuRepository, userRepository))
    }

    @Test
    fun getMenu() {
        every { menuRepository.getMenu(any()) } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getMenu().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { menuRepository.getMenu(any()) }
    }

    @Test
    fun getCategories() {
        every { categoryRepository.getCategory() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getCategories().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { categoryRepository.getCategory() }
    }

    @Test
    fun getCurrentUser() {
        val fakeUser = mockk<User>()
        every { userRepository.getCurrentUser() } returns fakeUser
        val result = viewModel.getCurrentUser()
        assertEquals(fakeUser, result)
        verify { userRepository.getCurrentUser() }
    }
}
