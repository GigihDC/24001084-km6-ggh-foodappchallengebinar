package com.dev.foodappchallengebinar.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import com.dev.foodappchallengebinar.R
import com.dev.foodappchallengebinar.data.models.Menu
import com.dev.foodappchallengebinar.databinding.ActivityDetailBinding
import com.dev.foodappchallengebinar.utils.proceedWhen
import com.dev.foodappchallengebinar.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity() {
    private lateinit var menu: Menu
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val detailViewModel: DetailViewModel by viewModel {
        parametersOf(intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindMenu(detailViewModel.menu)
        setClickListener()
        observeData()
    }

    private fun setClickListener() {
        binding.ibBack.setOnClickListener {
            onBackPressed()
        }
        binding.layoutCart.ivMinus.setOnClickListener {
            detailViewModel.minus()
        }
        binding.layoutCart.ivPlus.setOnClickListener {
            detailViewModel.add()
        }
        binding.svDetailFood.layoutLocation.cardLocation.setOnClickListener {
            openLocationOnMaps()
        }
        binding.layoutCart.btnAddToCart.setOnClickListener {
            addMenuToCart()
        }
    }

    private fun addMenuToCart() {
        detailViewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    Toast.makeText(
                        this,
                        getString(R.string.text_add_to_cart_success),
                        Toast.LENGTH_SHORT,
                    ).show()
                    finish()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    Toast.makeText(this, getString(R.string.add_to_cart_failed), Toast.LENGTH_SHORT)
                        .show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                },
            )
        }
    }

    private fun openLocationOnMaps() {
        val uri = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            intent.setPackage(null)
            startActivity(intent)
        }
    }

    private fun bindMenu(menu: Menu?) {
        this@DetailActivity.menu = intent.getParcelableExtra<Menu>("EXTRAS") ?: return
        menu?.let { item ->
            binding.svDetailFood.layoutDetail.tvMenuName.text = menu.name
            binding.svDetailFood.layoutDetail.tvMenuPrice.text = menu.price.toIndonesianFormat()
            binding.svDetailFood.ivMenuImage.load(item.imgUrl) {
                crossfade(true)
            }
            binding.svDetailFood.layoutDetail.tvMenuDescription.text = menu.desc
            binding.svDetailFood.layoutLocation.tvRestoAddress.text = menu.address
        }
    }

    private fun observeData() {
        detailViewModel.priceLiveData.observe(this) {
            binding.layoutCart.btnAddToCart.isEnabled = it != 0.0
            binding.layoutCart.tvMenuPriceCount.text = it.toIndonesianFormat()
        }
        detailViewModel.menuCountLiveData.observe(this) {
            binding.layoutCart.tvCountNumber.text = it.toString()
        }
    }

    companion object {
        const val EXTRAS = "EXTRAS"

        fun startActivity(
            context: Context,
            menu: Menu,
        ) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRAS, menu)
            context.startActivity(intent)
        }
    }
}
