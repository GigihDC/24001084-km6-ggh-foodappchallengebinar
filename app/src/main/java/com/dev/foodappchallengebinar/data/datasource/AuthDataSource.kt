package com.dev.foodappchallengebinar.data.datasource

import android.net.Uri
import com.dev.foodappchallengebinar.data.models.User
import com.dev.foodappchallengebinar.data.models.toUser
import com.dev.foodappchallengebinar.data.source.firebase.FirebaseService

interface AuthDataSource {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(
        email: String,
        fullName: String,
        password: String,
    ): Boolean

    suspend fun updateProfile(
        fullName: String?,
        photoUri: Uri?,
    ): Boolean

    suspend fun updatePassword(newPassword: String): Boolean

    suspend fun updateEmail(newEmail: String): Boolean

    fun requestChangePasswordByEmail(): Boolean

    fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): User?
}

class FirebaseAuthDataSource(private val service: FirebaseService) : AuthDataSource {
    override suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean {
        return service.doLogin(email, password)
    }

    override suspend fun doRegister(
        email: String,
        fullName: String,
        password: String,
    ): Boolean {
        return service.doRegister(email, fullName, password)
    }

    override suspend fun updateProfile(
        fullName: String?,
        photoUri: Uri?,
    ): Boolean {
        return service.updateProfile(fullName)
    }

    override suspend fun updateEmail(newEmail: String): Boolean {
        return service.updateEmail(newEmail)
    }

    override suspend fun updatePassword(newPassword: String): Boolean {
        return service.updatePassword(newPassword)
    }

    override fun requestChangePasswordByEmail(): Boolean {
        return service.requestChangePasswordByEmail()
    }

    override fun getCurrentUser(): User? {
        return service.getCurrentUser().toUser()
    }

    override fun isLoggedIn(): Boolean {
        return service.isLoggedIn()
    }

    override fun doLogout(): Boolean {
        return service.doLogout()
    }
}
