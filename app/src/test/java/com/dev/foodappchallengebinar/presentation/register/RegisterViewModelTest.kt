package com.dev.foodappchallengebinar.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dev.foodappchallengebinar.data.repository.UserRepository
import com.dev.foodappchallengebinar.tools.MainCoroutineRule
import com.dev.foodappchallengebinar.tools.getOrAwaitValue
import com.dev.foodappchallengebinar.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RegisterViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(RegisterViewModel(repo))
    }

    @Test
    fun doRegister_success() {
        val email = "example@example.com"
        val fullName = "Test"
        val password = "password"
        val expectedResult = ResultWrapper.Success(true)
        coEvery { repo.doRegister(email, fullName, password) } returns flow { emit(expectedResult) }
        val result = viewModel.doRegister(email, fullName, password).getOrAwaitValue()
        Assert.assertTrue(result is ResultWrapper.Success)
    }

    @Test
    fun doRegister_error() {
        val email = "example@example.com"
        val fullName = "Test"
        val password = "password"
        val expectedException = Exception("Register Failed")
        val expectedResult: ResultWrapper<Boolean> = ResultWrapper.Error(expectedException)
        val flowResult: Flow<ResultWrapper<Boolean>> = flow { emit(expectedResult) }
        coEvery { repo.doRegister(email, fullName, password) } returns flowResult
        val result = viewModel.doRegister(email, fullName, password).getOrAwaitValue()
        Assert.assertTrue(result is ResultWrapper.Error)
    }
}
