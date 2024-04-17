package com.dev.foodappchallengebinar.data.source.network.model.menu

import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @SerializedName("status")
    val status : Boolean?,
    @SerializedName("code")
    val code : Int?,
    @SerializedName("message")
    val message : String?,
    @SerializedName("data")
    val data : List<FoodItemResponse>?,
)
