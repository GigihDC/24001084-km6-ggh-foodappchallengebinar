package com.dev.foodappchallengebinar.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dev.foodappchallengebinar.R
import com.dev.foodappchallengebinar.databinding.ActivityMainBinding
import com.dev.foodappchallengebinar.presentation.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainViewModel: MainViewModel by viewModel()

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
                    if (!mainViewModel.isUserLoggedIn()) {
                        Toast.makeText(
                            this,
                            getString(R.string.text_please_login),
                            Toast.LENGTH_SHORT,
                        ).show()
                        navigateToLogin()
                        controller.popBackStack(R.id.navigation_home, false)
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            },
        )
    }
}
