package com.dev.foodappchallengebinar.data.repository

import com.dev.foodappchallengebinar.data.datasource.category.FoodCategoryDataSource
import com.dev.foodappchallengebinar.data.mapper.toCategories
import com.dev.foodappchallengebinar.data.models.Category
import com.dev.foodappchallengebinar.utils.ResultWrapper
import com.dev.foodappchallengebinar.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategory(): Flow<ResultWrapper<List<Category>>>
}

class CategoryRepositoryImpl(private val dataSource: FoodCategoryDataSource) : CategoryRepository {
    override fun getCategory(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow { dataSource.getFoodCategory().data.toCategories() }
    }
}