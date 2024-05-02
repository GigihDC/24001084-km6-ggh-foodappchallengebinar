package com.dev.foodappchallengebinar.data.datasource.category

import com.dev.foodappchallengebinar.data.source.network.model.category.CategoriesResponse
import com.dev.foodappchallengebinar.data.source.network.services.FoodAppApiService

class FoodCategoryApiDataSource(
    private val service: FoodAppApiService,
) : FoodCategoryDataSource {
    override suspend fun getFoodCategory(): CategoriesResponse {
        return service.getCategories()
    }
}
