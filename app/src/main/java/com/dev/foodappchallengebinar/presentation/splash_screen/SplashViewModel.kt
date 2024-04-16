package com.dev.foodappchallengebinar.presentation.splash_screen

import androidx.lifecycle.ViewModel
import com.dev.foodappchallengebinar.data.repository.UserRepository

class SplashViewModel(private val repository: UserRepository) : ViewModel() {
    fun isUserLoggedIn() = repository.isLoggedIn()
}