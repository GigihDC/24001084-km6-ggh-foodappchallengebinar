package com.dev.foodappchallengebinar.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dev.foodappchallengebinar.data.repository.CategoryRepository
import com.dev.foodappchallengebinar.data.repository.MenuRepository
import com.dev.foodappchallengebinar.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {
    fun isUserLoggedIn(): Boolean = userRepository.isLoggedIn()
    fun doLogout(): Boolean = userRepository.doLogout()
    fun getMenu(categoryName: String? = null) =
        menuRepository.getMenu(categoryName).asLiveData(Dispatchers.IO)

    fun getCategories() = categoryRepository.getCategory().asLiveData(Dispatchers.IO)
}