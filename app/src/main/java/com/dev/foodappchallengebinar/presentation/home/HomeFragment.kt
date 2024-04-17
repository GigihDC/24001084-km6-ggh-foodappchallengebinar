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
import com.dev.foodappchallengebinar.data.datasource.category.DummyFoodCategoryDataSource
import com.dev.foodappchallengebinar.data.datasource.menu.DummyFoodDataSource
import com.dev.foodappchallengebinar.data.models.Menu
import com.dev.foodappchallengebinar.data.repository.CategoryRepository
import com.dev.foodappchallengebinar.data.repository.CategoryRepositoryImpl
import com.dev.foodappchallengebinar.data.repository.MenuRepository
import com.dev.foodappchallengebinar.data.repository.MenuRepositoryImpl
import com.dev.foodappchallengebinar.data.source.local.pref.UserPreference
import com.dev.foodappchallengebinar.data.source.local.pref.UserPreferenceImpl
import com.dev.foodappchallengebinar.databinding.FragmentHomeBinding
import com.dev.foodappchallengebinar.presentation.detail.DetailActivity
import com.dev.foodappchallengebinar.presentation.home.adapters.category.CategoryAdapter
import com.dev.foodappchallengebinar.presentation.home.adapters.menu.MenuAdapter
import com.dev.foodappchallengebinar.presentation.home.adapters.menu.OnItemClickedListener
import com.dev.foodappchallengebinar.presentation.login.LoginActivity
import com.dev.foodappchallengebinar.utils.GenericViewModelFactory

class HomeFragment : Fragment() {

    private val userPreference: UserPreference by lazy {
        UserPreferenceImpl(requireContext())
    }
    private lateinit var binding: FragmentHomeBinding
    private val adapterCategory = CategoryAdapter()
    private var adapterMenu: MenuAdapter? = null
    private val viewModel: HomeViewModel by viewModels {
        val dataSourceMenu = DummyFoodDataSource()
        val menuRepository: MenuRepository = MenuRepositoryImpl(dataSourceMenu)
        val dataSourceCategory = DummyFoodCategoryDataSource()
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(dataSourceCategory)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository))
    }

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
            bindMenuList(newListMode)
        }
        binding.layoutHeader.btnLogout.setOnClickListener {
            doLogout()
        }
    }

    private fun doLogout() {
        val dialog = AlertDialog.Builder(requireContext()).setMessage("Apakah kamu ingin logout ?")
            .setPositiveButton(
                "Ya"
            ) { dialog, id ->
                navigateToLogin()
            }
            .setNegativeButton(
                "Tidak"
            ) { dialog, id ->
                dialog.dismiss()
            }.create()
        dialog.show()
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

    private fun setListCategory() {
        binding.rvCategory.adapter = adapterCategory
        adapterCategory.submitData(viewModel.getCategories())
    }

    private fun setListMenu() {
        bindMenuList(userPreference.getListMode())
        adapterMenu?.submitData(viewModel.getMenu())
    }

    private fun bindMenuList(listMode: Int) {
        adapterMenu =
            MenuAdapter(listMode = listMode, listener = object : OnItemClickedListener<Menu> {
                override fun onItemClicked(item: Menu) {
                    onItemClick(item)
                }
            })
        binding.rvMenu.apply {
            adapter = this@HomeFragment.adapterMenu
            layoutManager =
                GridLayoutManager(requireContext(), if (listMode == MenuAdapter.MODE_GRID) 2 else 1)
        }
        adapterMenu?.submitData(viewModel.getMenu())
    }
}
