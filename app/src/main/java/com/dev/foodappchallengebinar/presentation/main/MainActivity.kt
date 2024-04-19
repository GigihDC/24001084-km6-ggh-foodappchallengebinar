package com.dev.foodappchallengebinar.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dev.foodappchallengebinar.R
import com.dev.foodappchallengebinar.data.datasource.AuthDataSource
import com.dev.foodappchallengebinar.data.datasource.FirebaseAuthDataSource
import com.dev.foodappchallengebinar.data.repository.UserRepository
import com.dev.foodappchallengebinar.data.repository.UserRepositoryImpl
import com.dev.foodappchallengebinar.data.source.firebase.FirebaseService
import com.dev.foodappchallengebinar.data.source.firebase.FirebaseServiceImpl
import com.dev.foodappchallengebinar.databinding.ActivityMainBinding
import com.dev.foodappchallengebinar.presentation.login.LoginActivity
import com.dev.foodappchallengebinar.utils.GenericViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: MainViewModel by viewModels {
        val s: FirebaseService = FirebaseServiceImpl()
        val ds: AuthDataSource = FirebaseAuthDataSource(s)
        val r: UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(MainViewModel(r))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setButtonNav()
    }

    private fun setButtonNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_profile -> {
                    if (!viewModel.isUserLoggedIn()) {
                        Toast.makeText(
                            this,
                            getString(R.string.text_please_login),
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateToLogin()
                        controller.popBackStack(R.id.navigation_home, false)
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        })
    }
}