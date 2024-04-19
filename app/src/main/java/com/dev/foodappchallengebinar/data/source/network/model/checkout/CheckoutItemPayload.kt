package com.dev.foodappchallengebinar.data.source.network.model.checkout

import com.google.gson.annotations.SerializedName

data class CheckoutItemPayload(
    @SerializedName("catatan")
    val notes: String?,
    @SerializedName("nama")
    val name: String,
    @SerializedName("qty")
    val quantity: Int,
    @SerializedName("harga")
    val price: Double
)
