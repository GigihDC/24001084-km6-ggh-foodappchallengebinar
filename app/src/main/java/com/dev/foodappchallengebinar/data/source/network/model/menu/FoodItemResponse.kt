package com.dev.foodappchallengebinar.data.source.network.model.menu

import com.google.gson.annotations.SerializedName

data class FoodItemResponse(
    @SerializedName("id")
    val id : String?,
    @SerializedName("nama")
    val name : String?,
    @SerializedName("image_url")
    val imgUrl : String?,
    @SerializedName("harga")
    val price : Double?,
    @SerializedName("detail")
    val desc : String?,
    @SerializedName("alamat_resto")
    val address : String?,
)