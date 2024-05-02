package com.dev.foodappchallengebinar.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.foodappchallengebinar.R
import com.dev.foodappchallengebinar.data.models.Category
import com.dev.foodappchallengebinar.data.models.Menu
import com.dev.foodappchallengebinar.data.source.local.pref.UserPreference
import com.dev.foodappchallengebinar.data.source.local.pref.UserPreferenceImpl
import com.dev.foodappchallengebinar.databinding.FragmentHomeBinding
import com.dev.foodappchallengebinar.presentation.detail.DetailActivity
import com.dev.foodappchallengebinar.presentation.home.adapters.category.CategoryAdapter
import com.dev.foodappchallengebinar.presentation.home.adapters.menu.MenuAdapter
import com.dev.foodappchallengebinar.presentation.home.adapters.menu.OnItemClickedListener
import com.dev.foodappchallengebinar.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private val userPreference: UserPreference by lazy {
        UserPreferenceImpl(requireContext())
    }
    private val adapterCategory: CategoryAdapter by lazy {
        CategoryAdapter {
            observeMenuData(it.name)
        }
    }
    private var adapterMenu: MenuAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setButtonIcon()
        observeCategoryData()
        observeMenuData()
        setClickAction()
    }

    private fun setClickAction() {
        binding.ivListSwitch.setOnClickListener {
            val isGridLayout = userPreference.getListMode() == MenuAdapter.MODE_GRID
            val newListMode = if (isGridLayout) MenuAdapter.MODE_LIST else MenuAdapter.MODE_GRID
            userPreference.setListMode(newListMode)
            setButtonIcon()
            observeMenuData()
        }
    }

    private fun setButtonIcon() {
        val isGridLayout = userPreference.getListMode() == MenuAdapter.MODE_GRID
        val newListMode = if (isGridLayout) MenuAdapter.MODE_LIST else MenuAdapter.MODE_GRID
        val iconResChange =
            if (newListMode == MenuAdapter.MODE_GRID) {
                R.drawable.ic_grid
            } else {
                R.drawable.ic_list
            }
        binding.ivListSwitch.setImageResource(iconResChange)
    }

    private fun onItemClick(menu: Menu) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("EXTRAS", menu)
        startActivity(intent)
    }

    private fun observeMenuData(categoryName: String? = null) {
        homeViewModel.getMenu(categoryName).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = { it ->
                    binding.pbLoadingMenu.isVisible = false
                    binding.tvEnd.isVisible = true
                    binding.rvMenu.isVisible = true
                    it.payload?.let { menus ->
                        val listMode = userPreference.getListMode()
                        val adapterMenu =
                            MenuAdapter(
                                listMode = listMode,
                                listener =
                                    object : OnItemClickedListener<Menu> {
                                        override fun onItemClicked(item: Menu) {
                                            onItemClick(item)
                                        }
                                    },
                            )
                        binding.rvMenu.apply {
                            adapter = adapterMenu
                            layoutManager =
                                GridLayoutManager(
                                    requireContext(),
                                    if (listMode == MenuAdapter.MODE_GRID) 2 else 1,
                                )
                        }
                        adapterMenu.submitData(menus)
                    }
                },
                doOnLoading = {
                    binding.pbLoadingMenu.isVisible = true
                    binding.tvEnd.isVisible = false
                    binding.rvMenu.isVisible = false
                },
                doOnError = {
                    binding.pbLoadingMenu.isVisible = false
                    binding.tvEnd.isVisible = false
                },
            )
        }
    }

    private fun setListCategory(data: List<Category>) {
        binding.rvCategory.adapter = adapterCategory
        adapterCategory.submitData(data)
    }

    private fun observeCategoryData() {
        homeViewModel.getCategories().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoadingCategory.isVisible = false
                    it.payload?.let { data -> setListCategory(data) }
                },
                doOnLoading = {
                    binding.pbLoadingCategory.isVisible = true
                },
                doOnError = {
                    binding.pbLoadingCategory.isVisible = false
                },
            )
        }
    }
}
