package com.dev.foodappchallengebinar.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    var id: String? = UUID.randomUUID().toString(),
    var imgUrl: String,
    var price: Double,
    var name: String,
    var desc: String,
    var address: String,
) : Parcelable
