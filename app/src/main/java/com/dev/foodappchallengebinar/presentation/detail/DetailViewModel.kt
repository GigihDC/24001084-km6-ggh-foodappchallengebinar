package com.dev.foodappchallengebinar.presentation.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dev.foodappchallengebinar.utils.ResultWrapper
import com.dev.foodappchallengebinar.data.models.Menu
import kotlinx.coroutines.Dispatchers
import java.lang.IllegalStateException

class DetailViewModel (
    private val extras: Bundle?,
//    private val cartRepository: CartRepository
) : ViewModel() {

    val menu = extras?.getParcelable<Menu>(DetailActivity.EXTRAS)

    val menuCountLiveData = MutableLiveData(0).apply {
        postValue(0)
    }

    val priceLiveData = MutableLiveData<Double>().apply {
        postValue(0.0)
    }

    fun add() {
        val count = (menuCountLiveData.value ?: 0) + 1
        menuCountLiveData.postValue(count)
        priceLiveData.postValue(menu?.price?.times(count) ?: 0.0)
    }

    fun minus() {
        if ((menuCountLiveData.value ?: 0) > 0) {
            val count = (menuCountLiveData.value ?: 0) - 1
            menuCountLiveData.postValue(count)
            priceLiveData.postValue(menu?.price?.times(count) ?: 0.0)
        }
    }

//    fun addToCart(): LiveData<ResultWrapper<Boolean>> {
//        return menu?.let {
//            val quantity = productCountLiveData.value ?: 0
//            cartRepository.createCart(it, quantity).asLiveData(Dispatchers.IO)
//        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Product not found"))) }
//    }
}