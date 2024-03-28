package com.dev.foodappchallengebinar.data.repository

import com.dev.foodappchallengebinar.data.datasource.menu.FoodDataSource
import com.dev.foodappchallengebinar.data.models.Menu

interface MenuRepository {
    fun getMenu(): List<Menu>
}

class MenuRepositoryImpl(private val dataSource: FoodDataSource) : MenuRepository {
    override fun getMenu(): List<Menu> = dataSource.getFoodDetail()
}