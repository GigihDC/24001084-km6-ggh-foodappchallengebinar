package com.dev.foodappchallengebinar.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dev.foodappchallengebinar.data.repository.UserRepository
import com.dev.foodappchallengebinar.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
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

//    @Test
//    fun doLogin_success() {
//        // Given
//        val email = "test@example.com"
//        val password = "password"
//        val fakeResult = ResultWrapper.Success(true)
//        every { userRepository.doLogin(email, password) } returns flow { emit(fakeResult) }
//
//        // When
//        val liveData = viewModel.doLogin(email, password)
//
//        // Then
//        assertEquals(fakeResult, liveData.value)
//    }

//    @Test
//    fun doLogin_error() {
//        val email = "test@example.com"
//        val password = "password"
//        val fakeResult = ResultWrapper.Error(Exception("Login failed"))
//        every { userRepository.doLogin(email, password) } returns flow { emit(fakeResult) }
//        val observer = Observer<ResultWrapper<Boolean>> {}
//        viewModel.doLogin(email, password).observeForever(observer)
//        assertEquals(fakeResult, viewModel.doLogin(email, password).value)
//    }
//
//    fun doLogin_nullResult() {
//        // Given
//        val email = "test@example.com"
//        val password = "password"
//        every { userRepository.doLogin(email, password) } returns null
//
//        // When
//        val liveData = viewModel.doLogin(email, password)
//
//        // Then
//        assertNull(liveData.value)
//    }
}
