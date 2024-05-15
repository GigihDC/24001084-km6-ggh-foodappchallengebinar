package com.dev.foodappchallengebinar.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dev.foodappchallengebinar.data.repository.UserRepository
import com.dev.foodappchallengebinar.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(MainViewModel(repo))
    }

    @Test
    fun isUserLoggedIn_is_success() {
        every { repo.isLoggedIn() } returns true
        val result = viewModel.isUserLoggedIn()
        assertTrue(result)
    }

    @Test
    fun isUserLoggedIn_error() {
        every { repo.isLoggedIn() } returns false
        val result = viewModel.isUserLoggedIn()
        assertFalse(result)
    }
}
