package com.dev.foodappchallengebinar.data.datasource.menu

import com.dev.foodappchallengebinar.data.source.network.model.menu.FoodResponse

interface FoodDataSource {
    suspend fun getFoodDetail(categorySlug: String? = null): FoodResponse
}