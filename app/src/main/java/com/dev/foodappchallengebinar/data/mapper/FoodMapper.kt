package com.dev.foodappchallengebinar.data.mapper

import com.dev.foodappchallengebinar.data.models.Menu
import com.dev.foodappchallengebinar.data.source.network.model.menu.FoodItemResponse

fun FoodItemResponse?.toMenu() =
    Menu(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        price = this?.price ?: 0.0,
        imgUrl = this?.imgUrl.orEmpty(),
        desc = this?.desc.orEmpty(),
        address = this?.address.orEmpty(),
    )

fun Collection<FoodItemResponse>?.toMenu() =
    this?.map {
        it.toMenu()
    } ?: listOf()
