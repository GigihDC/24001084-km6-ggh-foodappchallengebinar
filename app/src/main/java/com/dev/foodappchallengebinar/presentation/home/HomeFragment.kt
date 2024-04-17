package com.dev.foodappchallengebinar.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.foodappchallengebinar.R
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
        val dataSourceMenu: FoodDataSource = FoodApiDataSource(FoodAppApiService.invoke())
        val menuRepository: MenuRepository = MenuRepositoryImpl(dataSourceMenu)
        val dataSourceCategory: FoodCategoryDataSource = FoodCategoryApiDataSource(FoodAppApiService.invoke())
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(dataSourceCategory)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository))
    }
    private val userPreference: UserPreference by lazy {
        UserPreferenceImpl(requireContext())
    }
    private val adapterCategory: CategoryAdapter by lazy {
        CategoryAdapter {
            getMenuData()
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
        setListCategory()
        setListMenu()
        setClickAction()
    }

    private fun setClickAction() {
        binding.ivListSwitch.setOnClickListener {
            val isGridLayout = userPreference.getListMode() == MenuAdapter.MODE_GRID
            val newListMode = if (isGridLayout) MenuAdapter.MODE_LIST else MenuAdapter.MODE_GRID
            userPreference.setListMode(newListMode)
            setButtonIcon(isGridLayout)
            bindMenuList(newListMode)
        }
        binding.layoutHeader.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun setButtonIcon(usingGridMode: Boolean) {
        val iconResChange = if (usingGridMode) {
            R.drawable.ic_grid
        } else {
            R.drawable.ic_list
        }
        binding.ivListSwitch.setImageResource(iconResChange)
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Apakah kamu ingin logout ?")
            .setPositiveButton("Ya") { dialog, id ->
                navigateToLogin()
            }
            .setNegativeButton("Tidak") { dialog, id ->
                dialog.dismiss()
            }
            .show()
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun onItemClick(menu: Menu) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("EXTRAS", menu)
        startActivity(intent)
    }

    private fun getMenuData(categorySlug: String? = null) {
        viewModel.getMenu(categorySlug).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> setListMenu(data) }
                }
            )
        }
    }
    private fun setListCategory() {
        getCategoryData()
    }
    private fun setListCategory(data: List<Category>) {
        binding.rvCategory.adapter = adapterCategory
        adapterCategory.submitData(data)
    }

    private fun getCategoryData() {
        viewModel.getCategories().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> setListCategory(data) }
                }
            )
        }
    }
    private fun setListMenu() {
        getMenuData()
    }
    private fun setListMenu(data: List<Menu>) {
        bindMenuList(userPreference.getListMode())
        adapterMenu?.submitData(data)
    }

    private fun bindMenuList(listMode: Int) {
        viewModel.getMenu().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = { menusResult ->
                    menusResult.payload?.let { menus ->
                        adapterMenu = MenuAdapter(listMode = listMode, listener = object : OnItemClickedListener<Menu> {
                            override fun onItemClicked(item: Menu) {
                                onItemClick(item)
                            }
                        })
                        binding.rvMenu.apply {
                            adapter = adapterMenu
                            layoutManager =
                                GridLayoutManager(requireContext(), if (listMode == MenuAdapter.MODE_GRID) 2 else 1)
                        }
                        adapterMenu?.submitData(menus)
                    }
                }
            )
        }
    }
}