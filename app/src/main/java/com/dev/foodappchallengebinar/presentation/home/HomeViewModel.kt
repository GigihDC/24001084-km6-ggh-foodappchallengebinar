package com.dev.foodappchallengebinar.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dev.foodappchallengebinar.data.repository.CategoryRepository
import com.dev.foodappchallengebinar.data.repository.MenuRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {
    fun getMenu(categorySlug: String? = null) =
        menuRepository.getMenu(categorySlug).asLiveData(Dispatchers.IO)
    fun getCategories() = categoryRepository.getCategory().asLiveData(Dispatchers.IO)
}