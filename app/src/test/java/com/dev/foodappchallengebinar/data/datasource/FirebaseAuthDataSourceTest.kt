package com.dev.foodappchallengebinar.data.datasource

import com.dev.foodappchallengebinar.data.models.User
import com.dev.foodappchallengebinar.data.source.firebase.FirebaseService
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceTest {
    @MockK
    lateinit var service: FirebaseService
    private lateinit var ds: AuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = FirebaseAuthDataSource(service)
    }

    @Test
    fun doLogin() {
        val email = "test@example.com"
        val password = "password"
        runBlocking {
            coEvery { service.doLogin(email, password) } returns true
            val result = ds.doLogin(email, password)
            assertEquals(true, result)
        }
    }

    @Test
    fun doRegister() {
        val email = "test@example.com"
        val fullName = "John Doe"
        val password = "password"
        runBlocking {
            coEvery { service.doRegister(email, fullName, password) } returns true
            val result = ds.doRegister(email, fullName, password)
            assertEquals(true, result)
        }
    }

    @Test
    fun updateProfile() {
        val fullName = "John Doe"
        runBlocking {
            coEvery { service.updateProfile(fullName) } returns true
            val result = ds.updateProfile(fullName, null)
            assertEquals(true, result)
        }
    }

    @Test
    fun updateProfile_is_null() {
        runBlocking {
            coEvery { service.updateProfile(null) } returns true
            val result = ds.updateProfile(null, null)
            assertEquals(true, result)
        }
    }

    @Test
    fun updateEmail() {
        val newEmail = "newemail@example.com"
        runBlocking {
            coEvery { service.updateEmail(newEmail) } returns true
            val result = ds.updateEmail(newEmail)
            assertEquals(true, result)
        }
    }

    @Test
    fun updatePassword() {
        val newPassword = "newpassword"
        runBlocking {
            coEvery { service.updatePassword(newPassword) } returns true
            val result = ds.updatePassword(newPassword)
            assertEquals(true, result)
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        runBlocking {
            coEvery { service.requestChangePasswordByEmail() } returns true
            val result = ds.requestChangePasswordByEmail()
            assertEquals(true, result)
        }
    }

    @Test
    fun getCurrentUser() {
        val firebaseUser = mockk<FirebaseUser>()
        val user = User("123456789", "John Doe", "newemail@example.com", "newpassword")
        every { service.getCurrentUser() } returns firebaseUser
        every { firebaseUser.getUid() } returns "123456789"
        every { firebaseUser.getDisplayName() } returns "John Doe"
        every { firebaseUser.getEmail() } returns "newemail@example.com"
        every { firebaseUser.getPhoneNumber() } returns "newpassword"
        val result = runBlocking { ds.getCurrentUser() }
        assertEquals(user, result)
    }

    @Test
    fun isLoggedIn() {
        runBlocking {
            coEvery { service.isLoggedIn() } returns true
            val result = ds.isLoggedIn()
            assertEquals(true, result)
        }
    }

    @Test
    fun doLogout() {
        runBlocking {
            coEvery { service.doLogout() } returns true
            val result = ds.doLogout()
            assertEquals(true, result)
        }
    }
}
