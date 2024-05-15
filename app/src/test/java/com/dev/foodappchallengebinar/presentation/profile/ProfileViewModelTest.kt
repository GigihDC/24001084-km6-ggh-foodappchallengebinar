package com.dev.foodappchallengebinar.presentation.profile

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dev.foodappchallengebinar.data.models.User
import com.dev.foodappchallengebinar.data.repository.UserRepository
import com.dev.foodappchallengebinar.tools.MainCoroutineRule
import com.dev.foodappchallengebinar.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProfileViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(ProfileViewModel(repo))
    }

    @Test
    fun getCurrentUser_success() {
        val fakeUser = mockk<User>()
        every { repo.getCurrentUser() } returns fakeUser
        val result = viewModel.getCurrentUser()
        assertEquals(fakeUser, result)
        verify { repo.getCurrentUser() }
    }

    @Test
    fun updateProfilePicture() =
        runBlockingTest {
            val photoUri: Uri = mockk()
            val expectedResult = ResultWrapper.Success(true)
            coEvery { repo.updateProfile(photoUri = any()) } returns flowOf(expectedResult)
            viewModel.updateProfilePicture(photoUri)
            advanceUntilIdle()
            delay(100)
            val observer = Observer<ResultWrapper<Boolean>> {}
            viewModel.changePhotoResult.observeForever(observer)
            val result = viewModel.changePhotoResult.value
            assertEquals(expectedResult, result)
            viewModel.changePhotoResult.removeObserver(observer)
        }

    @Test
    fun updateFullName() =
        runBlockingTest {
            val fullName = "John Doe"
            val expectedResult = ResultWrapper.Success(true)
            coEvery { repo.updateProfile(fullName = any()) } returns flowOf(expectedResult)
            viewModel.updateFullName(fullName)
            delay(100)
            val observer = Observer<ResultWrapper<Boolean>> {}
            viewModel.changeProfileResult.observeForever(observer)
            val result = viewModel.changeProfileResult.value
            assertEquals(expectedResult, result)
            viewModel.changeProfileResult.removeObserver(observer)
        }

    @Test
    fun createChangePwdRequest() {
        coEvery { repo.requestChangePasswordByEmail() } coAnswers { true }
        viewModel.createChangePwdRequest()
        coVerify { repo.requestChangePasswordByEmail() }
    }

    @Test
    fun doLogout() {
        coEvery { repo.doLogout() } coAnswers { true }
        viewModel.doLogout()
        coVerify { repo.doLogout() }
    }
}
