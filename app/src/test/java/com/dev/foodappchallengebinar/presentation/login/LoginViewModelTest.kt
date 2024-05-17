package com.dev.foodappchallengebinar.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dev.foodappchallengebinar.data.repository.UserRepository
import com.dev.foodappchallengebinar.tools.MainCoroutineRule
import com.dev.foodappchallengebinar.tools.getOrAwaitValue
import com.dev.foodappchallengebinar.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LoginViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(LoginViewModel(userRepository))
    }

    @Test
    fun doLogin_success() {
        val email = "example@example.com"
        val password = "password"
        val expectedResult = ResultWrapper.Success(true)
        every { userRepository.doLogin(email, password) } returns flow { emit(expectedResult) }
        val result = viewModel.doLogin(email, password).getOrAwaitValue()
        Assert.assertTrue(result is ResultWrapper.Success)
    }

    @Test
    fun doLogin_error() {
        val email = "example@example.com"
        val password = "password"
        val expectedException = Exception("Login Failed")
        val expectedResult: ResultWrapper<Boolean> = ResultWrapper.Error(expectedException)
        val flowResult: Flow<ResultWrapper<Boolean>> = flow { emit(expectedResult) }
        coEvery { userRepository.doLogin(email, password) } returns flowResult
        val result = viewModel.doLogin(email, password).getOrAwaitValue()
        Assert.assertTrue(result is ResultWrapper.Error)
    }
}
