package com.dev.foodappchallengebinar.presentation.splash_screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dev.foodappchallengebinar.data.datasource.AuthDataSource
import com.dev.foodappchallengebinar.data.datasource.FirebaseAuthDataSource
import com.dev.foodappchallengebinar.data.repository.UserRepository
import com.dev.foodappchallengebinar.data.repository.UserRepositoryImpl
import com.dev.foodappchallengebinar.data.source.firebase.FirebaseService
import com.dev.foodappchallengebinar.data.source.firebase.FirebaseServiceImpl
import com.dev.foodappchallengebinar.databinding.ActivitySplashScreenBinding
import com.dev.foodappchallengebinar.presentation.main.MainActivity
import com.dev.foodappchallengebinar.utils.GenericViewModelFactory

class SplashScreenActivity : AppCompatActivity() {

    private val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    private val viewModel: SplashViewModel by viewModels {
        val s: FirebaseService = FirebaseServiceImpl()
        val ds: AuthDataSource = FirebaseAuthDataSource(s)
        val r: UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(SplashViewModel(r))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navigateToMain()
    }

    private fun navigateToMain() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }, 2000)
    }

}