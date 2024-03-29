package com.dev.foodappchallengebinar.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dev.foodappchallengebinar.data.repository.CartRepository
import com.dev.foodappchallengebinar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class CheckoutViewModel(private val cartRepository: CartRepository) : ViewModel() {

    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    fun deleteAll(): Flow<ResultWrapper<Boolean>> {
        return cartRepository.deleteAll()
    }

}