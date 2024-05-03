package com.dev.foodappchallengebinar.data.repository

import com.dev.foodappchallengebinar.data.datasource.menu.FoodDataSource
import com.dev.foodappchallengebinar.data.mapper.toMenu
import com.dev.foodappchallengebinar.data.models.Cart
import com.dev.foodappchallengebinar.data.models.Menu
import com.dev.foodappchallengebinar.data.source.network.model.checkout.CheckoutItemPayload
import com.dev.foodappchallengebinar.data.source.network.model.checkout.CheckoutRequestPayload
import com.dev.foodappchallengebinar.utils.ResultWrapper
import com.dev.foodappchallengebinar.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenu(categoryName: String? = null): Flow<ResultWrapper<List<Menu>>>

    fun createOrder(menu: List<Cart>): Flow<ResultWrapper<Boolean>>
}

class MenuRepositoryImpl(private val dataSource: FoodDataSource) : MenuRepository {
    override fun getMenu(categoryName: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow { dataSource.getFoodDetail(categoryName).data.toMenu() }
    }

    override fun createOrder(menu: List<Cart>): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.createOrder(
                CheckoutRequestPayload(
                    orders =
                        menu.map {
                            CheckoutItemPayload(
                                notes = it.itemNotes,
                                name = it.menuName,
                                quantity = it.itemQuantity,
                                price = it.menuPrice,
                            )
                        },
                ),
            ).status ?: false
        }
    }
}
