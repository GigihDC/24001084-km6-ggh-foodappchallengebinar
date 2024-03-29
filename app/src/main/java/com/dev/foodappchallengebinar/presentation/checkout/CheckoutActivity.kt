package com.dev.foodappchallengebinar.presentation.checkout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.dev.foodappchallengebinar.R
import com.dev.foodappchallengebinar.data.datasource.cart.CartDataSource
import com.dev.foodappchallengebinar.data.datasource.cart.CartDatabaseDataSource
import com.dev.foodappchallengebinar.data.repository.CartRepository
import com.dev.foodappchallengebinar.data.repository.CartRepositoryImpl
import com.dev.foodappchallengebinar.data.source.local.database.AppDatabase
import com.dev.foodappchallengebinar.databinding.ActivityCheckoutBinding
import com.dev.foodappchallengebinar.presentation.checkout.adapter.PriceListAdapter
import com.dev.foodappchallengebinar.presentation.common.adapter.CartListAdapter
import com.dev.foodappchallengebinar.presentation.main.MainActivity
import com.dev.foodappchallengebinar.utils.GenericViewModelFactory
import com.dev.foodappchallengebinar.utils.proceedWhen
import com.dev.foodappchallengebinar.utils.toIndonesianFormat
import kotlinx.coroutines.launch

class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(CheckoutViewModel(rp))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }
    private val priceItemAdapter: PriceListAdapter by lazy {
        PriceListAdapter {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnCheckout.setOnClickListener {
            dialogCheckout()
        }
    }

    private fun dialogCheckout() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_checkout_success, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)

        val dialog = builder.create()

        dialogView.findViewById<Button>(R.id.btn_close).setOnClickListener {
            dialog.dismiss()
            deleteAllCarts()
        }

        dialog.show()
    }


    private fun deleteAllCarts() {
        lifecycleScope.launch {
            viewModel.deleteAll().collect { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        val intent = Intent(this@CheckoutActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }

    private fun setupList() {
        binding.layoutContent.rvCart.adapter = adapter
        binding.layoutContent.rvShoppingSummary.adapter = priceItemAdapter
    }

    private fun observeData() {
        viewModel.checkoutData.observe(this) { result ->
            result.proceedWhen(doOnSuccess = {
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = true
                binding.layoutContent.rvCart.isVisible = true
                binding.cvSectionOrder.isVisible = true
                result.payload?.let { (carts, priceItems, totalPrice) ->
                    adapter.submitData(carts)
                    binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    priceItemAdapter.submitData(priceItems)
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
            }, doOnEmpty = { data ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                data.payload?.let { (_, _, totalPrice) ->
                    binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                }
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
            })
        }
    }
}