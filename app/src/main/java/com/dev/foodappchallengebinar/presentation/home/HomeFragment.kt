package com.dev.foodappchallengebinar.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.foodappchallengebinar.R
import com.dev.foodappchallengebinar.data.datasource.AuthDataSource
import com.dev.foodappchallengebinar.data.datasource.FirebaseAuthDataSource
import com.dev.foodappchallengebinar.data.datasource.category.FoodCategoryApiDataSource
import com.dev.foodappchallengebinar.data.datasource.category.FoodCategoryDataSource
import com.dev.foodappchallengebinar.data.datasource.menu.FoodApiDataSource
import com.dev.foodappchallengebinar.data.datasource.menu.FoodDataSource
import com.dev.foodappchallengebinar.data.models.Category
import com.dev.foodappchallengebinar.data.models.Menu
import com.dev.foodappchallengebinar.data.repository.CategoryRepository
import com.dev.foodappchallengebinar.data.repository.CategoryRepositoryImpl
import com.dev.foodappchallengebinar.data.repository.MenuRepository
import com.dev.foodappchallengebinar.data.repository.MenuRepositoryImpl
import com.dev.foodappchallengebinar.data.repository.UserRepository
import com.dev.foodappchallengebinar.data.repository.UserRepositoryImpl
import com.dev.foodappchallengebinar.data.source.firebase.FirebaseService
import com.dev.foodappchallengebinar.data.source.firebase.FirebaseServiceImpl
import com.dev.foodappchallengebinar.data.source.local.pref.UserPreference
import com.dev.foodappchallengebinar.data.source.local.pref.UserPreferenceImpl
import com.dev.foodappchallengebinar.data.source.network.services.FoodAppApiService
import com.dev.foodappchallengebinar.databinding.FragmentHomeBinding
import com.dev.foodappchallengebinar.presentation.detail.DetailActivity
import com.dev.foodappchallengebinar.presentation.home.adapters.category.CategoryAdapter
import com.dev.foodappchallengebinar.presentation.home.adapters.menu.MenuAdapter
import com.dev.foodappchallengebinar.presentation.home.adapters.menu.OnItemClickedListener
import com.dev.foodappchallengebinar.presentation.login.LoginActivity
import com.dev.foodappchallengebinar.utils.GenericViewModelFactory
import com.dev.foodappchallengebinar.utils.proceedWhen

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        val s: FirebaseService = FirebaseServiceImpl()
        val ds: AuthDataSource = FirebaseAuthDataSource(s)
        val r: UserRepository = UserRepositoryImpl(ds)
        val dataSourceMenu: FoodDataSource = FoodApiDataSource(FoodAppApiService.invoke())
        val menuRepository: MenuRepository = MenuRepositoryImpl(dataSourceMenu)
        val dataSourceCategory: FoodCategoryDataSource =
            FoodCategoryApiDataSource(FoodAppApiService.invoke())
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(dataSourceCategory)
        GenericViewModelFactory.create(HomeViewModel(r, categoryRepository, menuRepository))
    }
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        val iconResChange = if (newListMode == MenuAdapter.MODE_GRID) {
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
        viewModel.getMenu(categoryName).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = { it ->
                    binding.pbLoadingMenu.isVisible = false
                    binding.tvEnd.isVisible = true
                    binding.rvMenu.isVisible = true
                    it.payload?.let { menus ->
                        val listMode = userPreference.getListMode()
                        val adapterMenu = MenuAdapter(
                            listMode = listMode,
                            listener = object : OnItemClickedListener<Menu> {
                                override fun onItemClicked(item: Menu) {
                                    onItemClick(item)
                                }
                            }
                        )
                        binding.rvMenu.apply {
                            adapter = adapterMenu
                            layoutManager = GridLayoutManager(
                                requireContext(),
                                if (listMode == MenuAdapter.MODE_GRID) 2 else 1
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
                }
            )
        }
    }

    private fun setListCategory(data: List<Category>) {
        binding.rvCategory.adapter = adapterCategory
        adapterCategory.submitData(data)
    }

    private fun observeCategoryData() {
        viewModel.getCategories().observe(viewLifecycleOwner) {
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
                }
            )
        }
    }
}