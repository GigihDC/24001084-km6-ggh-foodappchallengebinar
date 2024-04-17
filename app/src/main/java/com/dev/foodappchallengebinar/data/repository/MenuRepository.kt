package com.dev.foodappchallengebinar.data.repository

import com.dev.foodappchallengebinar.data.datasource.menu.FoodDataSource
import com.dev.foodappchallengebinar.data.mapper.toMenu
import com.dev.foodappchallengebinar.data.models.Menu
import com.dev.foodappchallengebinar.utils.ResultWrapper
import com.dev.foodappchallengebinar.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenu(categorySlug: String? = null): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(private val dataSource: FoodDataSource) : MenuRepository {
    override fun getMenu(categorySlug: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow { dataSource.getFoodDetail(categorySlug).data.toMenu() }
    }
}