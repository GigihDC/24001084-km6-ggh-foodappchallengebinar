package com.dev.foodappchallengebinar.data.repository

import android.net.Uri
import com.dev.foodappchallengebinar.data.datasource.AuthDataSource
import com.dev.foodappchallengebinar.data.models.User
import com.dev.foodappchallengebinar.utils.ResultWrapper
import com.dev.foodappchallengebinar.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    @Throws(exceptionClasses = [Exception::class])
    fun doLogin(
        email: String,
        password: String,
    ): Flow<ResultWrapper<Boolean>>

    @Throws(exceptionClasses = [Exception::class])
    fun doRegister(
        email: String,
        fullName: String,
        password: String,
    ): Flow<ResultWrapper<Boolean>>

    fun updateProfile(
        fullName: String? = null,
        photoUri: Uri? = null,
    ): Flow<ResultWrapper<Boolean>>

    fun updatePassword(newPassword: String): Flow<ResultWrapper<Boolean>>

    fun updateEmail(newEmail: String): Flow<ResultWrapper<Boolean>>

    fun requestChangePasswordByEmail(): Boolean

    fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): User?
}

class UserRepositoryImpl(private val dataSource: AuthDataSource) : UserRepository {
    override fun doLogin(
        email: String,
        password: String,
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.doLogin(email, password) }
    }

    override fun doRegister(
        email: String,
        fullName: String,
        password: String,
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.doRegister(email, fullName, password) }
    }

    override fun updateProfile(
        fullName: String?,
        photoUri: Uri?,
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateProfile(fullName, photoUri) }
    }

    override fun updatePassword(newPassword: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updatePassword(newPassword) }
    }

    override fun updateEmail(newEmail: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateEmail(newEmail) }
    }

    override fun requestChangePasswordByEmail(): Boolean {
        return dataSource.requestChangePasswordByEmail()
    }

    override fun doLogout(): Boolean {
        return dataSource.doLogout()
    }

    override fun isLoggedIn(): Boolean {
        return dataSource.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return dataSource.getCurrentUser()
    }
}
