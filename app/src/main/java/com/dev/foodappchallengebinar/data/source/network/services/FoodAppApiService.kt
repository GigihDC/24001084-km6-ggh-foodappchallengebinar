package com.dev.foodappchallengebinar.data.source.network.services

import com.dev.foodappchallengebinar.BuildConfig
import com.dev.foodappchallengebinar.data.source.network.model.category.CategoriesResponse
import com.dev.foodappchallengebinar.data.source.network.model.menu.FoodResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface FoodAppApiService {

    @GET("category")
    suspend fun getCategories(): CategoriesResponse

    @GET("listmenu")
    suspend fun getFood(@Query("category") category: String? = null): FoodResponse

    companion object {
        @JvmStatic
        operator fun invoke(): FoodAppApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(FoodAppApiService::class.java)
        }
    }
}