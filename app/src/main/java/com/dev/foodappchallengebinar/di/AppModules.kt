package com.dev.foodappchallengebinar.di

import com.dev.foodappchallengebinar.data.datasource.AuthDataSource
import com.dev.foodappchallengebinar.data.datasource.FirebaseAuthDataSource
import com.dev.foodappchallengebinar.data.datasource.cart.CartDataSource
import com.dev.foodappchallengebinar.data.datasource.cart.CartDatabaseDataSource
import com.dev.foodappchallengebinar.data.datasource.category.FoodCategoryApiDataSource
import com.dev.foodappchallengebinar.data.datasource.category.FoodCategoryDataSource
import com.dev.foodappchallengebinar.data.datasource.menu.FoodApiDataSource
import com.dev.foodappchallengebinar.data.datasource.menu.FoodDataSource
import com.dev.foodappchallengebinar.data.repository.CartRepository
import com.dev.foodappchallengebinar.data.repository.CartRepositoryImpl
import com.dev.foodappchallengebinar.data.repository.CategoryRepository
import com.dev.foodappchallengebinar.data.repository.CategoryRepositoryImpl
import com.dev.foodappchallengebinar.data.repository.MenuRepository
import com.dev.foodappchallengebinar.data.repository.MenuRepositoryImpl
import com.dev.foodappchallengebinar.data.repository.UserRepository
import com.dev.foodappchallengebinar.data.repository.UserRepositoryImpl
import com.dev.foodappchallengebinar.data.source.firebase.FirebaseService
import com.dev.foodappchallengebinar.data.source.firebase.FirebaseServiceImpl
import com.dev.foodappchallengebinar.data.source.local.database.AppDatabase
import com.dev.foodappchallengebinar.data.source.local.database.dao.CartDao
import com.dev.foodappchallengebinar.data.source.local.pref.UserPreference
import com.dev.foodappchallengebinar.data.source.local.pref.UserPreferenceImpl
import com.dev.foodappchallengebinar.data.source.network.services.FoodAppApiService
import com.dev.foodappchallengebinar.presentation.cart.CartViewModel
import com.dev.foodappchallengebinar.presentation.checkout.CheckoutViewModel
import com.dev.foodappchallengebinar.presentation.detail.DetailViewModel
import com.dev.foodappchallengebinar.presentation.home.HomeViewModel
import com.dev.foodappchallengebinar.presentation.login.LoginViewModel
import com.dev.foodappchallengebinar.presentation.main.MainViewModel
import com.dev.foodappchallengebinar.presentation.profile.ProfileViewModel
import com.dev.foodappchallengebinar.presentation.register.RegisterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {
    private val networkModule =
        module {
            single<FoodAppApiService> { FoodAppApiService.invoke() }
        }

    private val firebaseModule =
        module {
            single<AuthDataSource> { FirebaseAuthDataSource(get()) }
            single<FirebaseService> { FirebaseServiceImpl() }
        }

    private val localModule =
        module {
            single<AppDatabase> { AppDatabase.createInstance(androidContext()) }
            single<CartDao> { get<AppDatabase>().cartDao() }
            single<UserPreference> { UserPreferenceImpl(get()) }
        }

    private val datasource =
        module {
            single<CartDataSource> { CartDatabaseDataSource(get()) }
            single<FoodCategoryDataSource> { FoodCategoryApiDataSource(get()) }
            single<FoodDataSource> { FoodApiDataSource(get()) }
        }

    private val repository =
        module {
            single<CartRepository> { CartRepositoryImpl(get()) }
            single<CategoryRepository> { CategoryRepositoryImpl(get()) }
            single<MenuRepository> { MenuRepositoryImpl(get()) }
            single<UserRepository> { UserRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::HomeViewModel)
            viewModelOf(::CartViewModel)
            viewModelOf(::CheckoutViewModel)
            viewModelOf(::LoginViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::ProfileViewModel)
            viewModelOf(::RegisterViewModel)
            viewModel { params ->
                DetailViewModel(
                    extras = params.get(),
                    cartRepository = get(),
                )
            }
        }

    val modules =
        listOf(
            networkModule,
            localModule,
            datasource,
            repository,
            firebaseModule,
            viewModelModule,
        )
}
