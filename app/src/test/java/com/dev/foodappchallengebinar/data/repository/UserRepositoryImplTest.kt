package com.dev.foodappchallengebinar.data.repository

import android.net.Uri
import com.dev.foodappchallengebinar.data.datasource.AuthDataSource
import com.dev.foodappchallengebinar.data.models.User
import com.dev.foodappchallengebinar.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class UserRepositoryImplTest {
    @MockK
    lateinit var ds: AuthDataSource
    private lateinit var repo: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = UserRepositoryImpl(ds)
    }

    @Test
    fun `doLogin success`() =
        runTest {
            val email = "test@example.com"
            val password = "password"
            coEvery { ds.doLogin(email, password) } returns true
            val result = mutableListOf<ResultWrapper<Boolean>>()
            repo.doLogin(email, password).collect {
                result.add(it)
            }
            println("Result: $result")
            Assert.assertEquals("Flow should emit two values", 2, result.size)
            Assert.assertTrue(
                "First result should be a Loading",
                result[0] is ResultWrapper.Loading<Boolean>,
            )
            Assert.assertTrue(
                "Second result should be a Success with payload true",
                result[1] is ResultWrapper.Success<Boolean> && (result[1] as ResultWrapper.Success<Boolean>).payload == true,
            )
            coVerify { ds.doLogin(email, password) }
        }

    @Test
    fun doRegisterSuccess() =
        runTest {
            val email = "test@example.com"
            val fullName = "Test User"
            val password = "password"
            coEvery { ds.doRegister(email, fullName, password) } returns true
            val result = mutableListOf<ResultWrapper<Boolean>>()
            repo.doRegister(email, fullName, password).collect {
                result.add(it)
            }
            println("Result: $result")
            Assert.assertEquals("Flow should emit two values", 2, result.size)
            Assert.assertTrue(
                "First result should be a Loading",
                result[0] is ResultWrapper.Loading<Boolean>,
            )
            Assert.assertTrue(
                "Second result should be a Success with payload true",
                result[1] is ResultWrapper.Success<Boolean> && (result[1] as ResultWrapper.Success<Boolean>).payload == true,
            )
            coVerify { ds.doRegister(email, fullName, password) }
        }

    @Test
    fun `updateProfile success`() =
        runTest {
            val fullName = "Updated User"
            val photoUri: Uri = mock(Uri::class.java)
            coEvery { ds.updateProfile(fullName, photoUri) } returns true
            val result = mutableListOf<ResultWrapper<Boolean>>()
            repo.updateProfile(fullName, photoUri).toList(result)
            println("Result: $result")
            Assert.assertTrue("Flow should emit Loading and then Success", result.size >= 2)
            Assert.assertTrue("First result should be Loading", result[0] is ResultWrapper.Loading)
            Assert.assertTrue(
                "Second result should be Success with payload true",
                result[1] is ResultWrapper.Success && (result[1] as ResultWrapper.Success).payload == true,
            )
        }

    @Test
    fun `updateProfile success is null`() =
        runTest {
            val fullName = null
            val photoUri = null
            coEvery { ds.updateProfile(fullName, photoUri) } returns true
            val result = mutableListOf<ResultWrapper<Boolean>>()
            repo.updateProfile(fullName, photoUri).toList(result)
            println("Result: $result")
            Assert.assertTrue("Flow should emit Loading and then Success", result.size >= 2)
            Assert.assertTrue("First result should be Loading", result[0] is ResultWrapper.Loading)
            Assert.assertTrue(
                "Second result should be Success with payload true",
                result[1] is ResultWrapper.Success<Boolean> && (result[1] as ResultWrapper.Success<Boolean>).payload == true,
            )
        }

    @Test
    fun `updateProfile success with null fullName`() =
        runTest {
            val fullName = null
            val photoUri = Uri.EMPTY // You can mock this if needed
            coEvery { ds.updateProfile(fullName, photoUri) } returns true
            val result = repo.updateProfile(fullName, photoUri).first()
            if (fullName != null) {
                Assert.assertTrue(result is ResultWrapper.Success)
            } else {
                Assert.assertFalse(result is ResultWrapper.Success)
            }
        }

    @Test
    fun `updateProfile success with null photoUri`() =
        runTest {
            val fullName = "John Doe" // You can mock this if needed
            val photoUri = null
            coEvery { ds.updateProfile(fullName, photoUri) } returns true
            val result = repo.updateProfile(fullName, photoUri).first()
            if (photoUri != null) {
                Assert.assertTrue(result is ResultWrapper.Success)
            } else {
                Assert.assertFalse(result is ResultWrapper.Success)
            }
        }

    @Test
    fun `updatePassword success`() =
        runTest {
            val newPassword = "newPassword"
            coEvery { ds.updatePassword(newPassword) } returns true
            val result = mutableListOf<ResultWrapper<Boolean>>()
            repo.updatePassword(newPassword).collect {
                result.add(it)
            }
            println("Result: $result")
            Assert.assertEquals("Flow should emit two values", 2, result.size)
            Assert.assertTrue(
                "First result should be a Loading",
                result[0] is ResultWrapper.Loading<Boolean>,
            )
            Assert.assertTrue(
                "Second result should be a Success with payload true",
                result[1] is ResultWrapper.Success<Boolean> && (result[1] as ResultWrapper.Success<Boolean>).payload == true,
            )
            coVerify { ds.updatePassword(newPassword) }
        }

    @Test
    fun `updateEmail success`() =
        runTest {
            val newEmail = "new@example.com"
            coEvery { ds.updateEmail(newEmail) } returns true
            val result = mutableListOf<ResultWrapper<Boolean>>()
            repo.updateEmail(newEmail).collect {
                result.add(it)
            }
            println("Result: $result")
            Assert.assertEquals("Flow should emit two values", 2, result.size)
            Assert.assertTrue(
                "First result should be a Loading",
                result[0] is ResultWrapper.Loading<Boolean>,
            )
            Assert.assertTrue(
                "Second result should be a Success with payload true",
                result[1] is ResultWrapper.Success<Boolean> && (result[1] as ResultWrapper.Success<Boolean>).payload == true,
            )
            coVerify { ds.updateEmail(newEmail) }
        }

    @Test
    fun `requestChangePasswordByEmail success`() {
        coEvery { ds.requestChangePasswordByEmail() } returns true
        val result = repo.requestChangePasswordByEmail()
        Assert.assertTrue(result)
    }

    @Test
    fun `doLogout success`() {
        coEvery { ds.doLogout() } returns true
        val result = repo.doLogout()
        Assert.assertTrue(result)
    }

    @Test
    fun `isLoggedIn success`() {
        coEvery { ds.isLoggedIn() } returns true
        val result = repo.isLoggedIn()
        Assert.assertTrue(result)
    }

    @Test
    fun `getCurrentUser success`() {
        val user = User("id", "Test User", "test@example.com", "password")
        coEvery { repo.getCurrentUser() } returns user
        val result = repo.getCurrentUser()
        Assert.assertEquals(user, result)
    }
}
