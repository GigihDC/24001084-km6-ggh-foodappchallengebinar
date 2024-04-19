package com.dev.foodappchallengebinar.presentation.main

import androidx.lifecycle.ViewModel
import com.dev.foodappchallengebinar.data.repository.UserRepository

class MainViewModel (private val repository: UserRepository) : ViewModel() {
    fun isUserLoggedIn() = repository.isLoggedIn()
}