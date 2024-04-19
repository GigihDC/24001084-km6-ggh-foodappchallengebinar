package com.dev.foodappchallengebinar.data.datasource.menu

import com.dev.foodappchallengebinar.data.source.network.model.checkout.CheckoutRequestPayload
import com.dev.foodappchallengebinar.data.source.network.model.checkout.CheckoutResponse
import com.dev.foodappchallengebinar.data.source.network.model.menu.FoodResponse
import com.dev.foodappchallengebinar.data.source.network.services.FoodAppApiService

class FoodApiDataSource (
    private val service: FoodAppApiService
) : FoodDataSource {
    override suspend fun getFoodDetail(categoryName: String?): FoodResponse {
        return service.getFood(categoryName)
    }
    override suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse {
        return service.createOrder(payload)
    }
}