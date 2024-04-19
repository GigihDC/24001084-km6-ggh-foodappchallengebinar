package com.dev.foodappchallengebinar.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dev.foodappchallengebinar.R
import com.dev.foodappchallengebinar.data.datasource.AuthDataSource
import com.dev.foodappchallengebinar.data.datasource.FirebaseAuthDataSource
import com.dev.foodappchallengebinar.data.datasource.cart.CartDataSource
import com.dev.foodappchallengebinar.data.datasource.cart.CartDatabaseDataSource
import com.dev.foodappchallengebinar.data.models.Cart
import com.dev.foodappchallengebinar.data.repository.CartRepository
import com.dev.foodappchallengebinar.data.repository.CartRepositoryImpl
import com.dev.foodappchallengebinar.data.repository.UserRepository
import com.dev.foodappchallengebinar.data.repository.UserRepositoryImpl
import com.dev.foodappchallengebinar.data.source.firebase.FirebaseService
import com.dev.foodappchallengebinar.data.source.firebase.FirebaseServiceImpl
import com.dev.foodappchallengebinar.data.source.local.database.AppDatabase
import com.dev.foodappchallengebinar.databinding.FragmentCartBinding
import com.dev.foodappchallengebinar.presentation.checkout.CheckoutActivity
import com.dev.foodappchallengebinar.presentation.common.adapter.CartListAdapter
import com.dev.foodappchallengebinar.presentation.common.adapter.CartListener
import com.dev.foodappchallengebinar.presentation.login.LoginActivity
import com.dev.foodappchallengebinar.utils.GenericViewModelFactory
import com.dev.foodappchallengebinar.utils.hideKeyboard
import com.dev.foodappchallengebinar.utils.proceedWhen
import com.dev.foodappchallengebinar.utils.toIndonesianFormat

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val viewModel: CartViewModel by viewModels() {
        val s: FirebaseService = FirebaseServiceImpl()
        val ads: AuthDataSource = FirebaseAuthDataSource(s)
        val r: UserRepository = UserRepositoryImpl(ads)
        val db = AppDatabase.getInstance(requireContext())
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(CartViewModel(r, rp))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(object : CartListener {
            override fun onMinusTotalItemCartClicked(cart: Cart) {
                viewModel.decreaseCart(cart)
            }

            override fun onPlusTotalItemCartClicked(cart: Cart) {
                viewModel.increaseCart(cart)
            }

            override fun onRemoveCartClicked(cart: Cart) {
                viewModel.removeCart(cart)
            }

            override fun onUserDoneEditingNotes(cart: Cart) {
                viewModel.setCartNotes(cart)
                hideKeyboard()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        setupList()
        observeData()
    }

    private fun setClickListeners() {
        binding.btnCheckout.setOnClickListener {
            if (viewModel.isUserLoggedIn()) {
                startActivity(Intent(requireContext(), CheckoutActivity::class.java))
            } else {
                navigateToLogin()
                Toast.makeText(
                    requireContext(),
                    getString(R.string.text_please_login),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        })
    }

    private fun observeData() {
        viewModel.getAllCarts().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvCart.isVisible = false
                    binding.btnCheckout.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvCart.isVisible = true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    }
                    binding.btnCheckout.isVisible = true
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                    binding.rvCart.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    binding.rvCart.isVisible = false
                    result.payload?.let { (carts, totalPrice) ->
                        binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    }
                    binding.btnCheckout.isVisible = false
                }
            )
        }
    }

    private fun setupList() {
        binding.rvCart.adapter = this@CartFragment.adapter
    }

}