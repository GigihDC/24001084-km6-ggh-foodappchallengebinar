package com.dev.foodappchallengebinar.data.mapper

import com.dev.foodappchallengebinar.data.models.Category
import com.dev.foodappchallengebinar.data.source.network.model.category.CategoryItemResponse

fun CategoryItemResponse?.toCategory() =
    Category(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        imgUrl = this?.imgUrl.orEmpty()
    )

fun Collection<CategoryItemResponse>?.toCategories() =
    this?.map { it.toCategory() } ?: listOf()