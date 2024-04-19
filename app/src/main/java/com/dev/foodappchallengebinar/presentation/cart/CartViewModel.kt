package com.dev.foodappchallengebinar.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dev.foodappchallengebinar.data.models.Cart
import com.dev.foodappchallengebinar.data.repository.CartRepository
import com.dev.foodappchallengebinar.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartViewModel(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository) : ViewModel() {
    fun isUserLoggedIn() = userRepository.isLoggedIn()
    fun getAllCarts() = cartRepository.getUserCartData().asLiveData(Dispatchers.IO)
    fun decreaseCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.decreaseCart(item).collect()
        }
    }

    fun increaseCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.increaseCart(item).collect()
        }
    }

    fun removeCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteCart(item).collect()
        }
    }

    fun setCartNotes(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.setCartNotes(item).collect()
        }
    }

}