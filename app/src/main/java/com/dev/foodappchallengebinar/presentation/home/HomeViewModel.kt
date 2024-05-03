package com.dev.foodappchallengebinar.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dev.foodappchallengebinar.data.repository.CategoryRepository
import com.dev.foodappchallengebinar.data.repository.MenuRepository
import com.dev.foodappchallengebinar.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    fun getMenu(categoryName: String? = null) = menuRepository.getMenu(categoryName).asLiveData(Dispatchers.IO)

    fun getCategories() = categoryRepository.getCategory().asLiveData(Dispatchers.IO)

    fun getCurrentUser() = userRepository.getCurrentUser()
}
