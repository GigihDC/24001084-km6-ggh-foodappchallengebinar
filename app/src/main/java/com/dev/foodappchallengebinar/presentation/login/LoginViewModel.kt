package com.dev.foodappchallengebinar.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dev.foodappchallengebinar.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun doLogin(
        email: String,
        password: String,
    ) = repository.doLogin(email, password).asLiveData(Dispatchers.IO)
}
