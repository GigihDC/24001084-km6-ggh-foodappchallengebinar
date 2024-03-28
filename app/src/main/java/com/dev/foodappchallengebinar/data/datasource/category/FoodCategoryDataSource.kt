package com.dev.foodappchallengebinar.data.datasource.category

import com.dev.foodappchallengebinar.data.models.Category

interface FoodCategoryDataSource {
    fun getFoodCategory(): List<Category>
}
