package com.dev.foodappchallengebinar.data.datasource.category

import com.dev.foodappchallengebinar.data.source.network.model.category.CategoriesResponse

interface FoodCategoryDataSource {
    suspend fun getFoodCategory(): CategoriesResponse
}
