package com.dev.foodappchallengebinar.presentation.home

import androidx.lifecycle.ViewModel
import com.dev.foodappchallengebinar.data.repository.CategoryRepository
import com.dev.foodappchallengebinar.data.repository.MenuRepository

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {
    fun getMenu() = menuRepository.getMenu()
    fun getCategories() = categoryRepository.getCategory()
}