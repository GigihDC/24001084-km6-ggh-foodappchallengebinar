package com.dev.foodappchallengebinar.data.repository

import com.dev.foodappchallengebinar.data.datasource.category.FoodCategoryDataSource
import com.dev.foodappchallengebinar.data.models.Category

interface CategoryRepository {
    fun getCategory(): List<Category>
}

class CategoryRepositoryImpl(private val dataSource: FoodCategoryDataSource) : CategoryRepository {
    override fun getCategory(): List<Category> = dataSource.getFoodCategory()
}