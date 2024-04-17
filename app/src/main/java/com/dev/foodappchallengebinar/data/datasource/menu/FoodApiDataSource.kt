package com.dev.foodappchallengebinar.data.datasource.menu

import com.dev.foodappchallengebinar.data.source.network.model.menu.FoodResponse
import com.dev.foodappchallengebinar.data.source.network.services.FoodAppApiService

class FoodApiDataSource (
    private val service: FoodAppApiService
) : FoodDataSource {
    override suspend fun getFoodDetail(categorySlug: String?): FoodResponse {
        return service.getFood(categorySlug)
    }
}