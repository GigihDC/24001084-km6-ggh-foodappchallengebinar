package com.dev.foodappchallengebinar.data.datasource.menu

import com.dev.foodappchallengebinar.data.models.Menu

interface FoodDataSource {
    fun getFoodDetail(): List<Menu>
}