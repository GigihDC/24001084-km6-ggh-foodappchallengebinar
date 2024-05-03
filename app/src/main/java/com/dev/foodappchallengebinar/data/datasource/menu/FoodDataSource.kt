package com.dev.foodappchallengebinar.data.datasource.menu

import com.dev.foodappchallengebinar.data.source.network.model.checkout.CheckoutRequestPayload
import com.dev.foodappchallengebinar.data.source.network.model.checkout.CheckoutResponse
import com.dev.foodappchallengebinar.data.source.network.model.menu.FoodResponse

interface FoodDataSource {
    suspend fun getFoodDetail(categoryName: String? = null): FoodResponse

    suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse
}
