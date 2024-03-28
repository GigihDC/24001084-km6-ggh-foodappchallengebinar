package com.dev.foodappchallengebinar.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.foodappchallengebinar.R
import com.dev.foodappchallengebinar.data.datasource.category.DummyFoodCategoryDataSource
import com.dev.foodappchallengebinar.data.datasource.menu.DummyFoodDataSource
import com.dev.foodappchallengebinar.data.models.Menu
import com.dev.foodappchallengebinar.data.repository.CategoryRepository
import com.dev.foodappchallengebinar.data.repository.CategoryRepositoryImpl
import com.dev.foodappchallengebinar.data.repository.MenuRepository
import com.dev.foodappchallengebinar.data.repository.MenuRepositoryImpl
import com.dev.foodappchallengebinar.databinding.FragmentHomeBinding
import com.dev.foodappchallengebinar.presentation.detail.DetailActivity
import com.dev.foodappchallengebinar.presentation.home.adapters.category.CategoryAdapter
import com.dev.foodappchallengebinar.presentation.home.adapters.menu.MenuAdapter
import com.dev.foodappchallengebinar.presentation.home.adapters.menu.OnItemClickedListener
import com.dev.foodappchallengebinar.utils.GenericViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapterCategory = CategoryAdapter()
    private var adapterMenu: MenuAdapter? = null
    private var isGridLayout = true
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
            isGridLayout = !isGridLayout
            setButtonIcon(isGridLayout)
            bindMenuList(isGridLayout)
        }
    }

    private fun setButtonIcon(usingGridMode: Boolean) {
        val iconResChange = if (usingGridMode) {
            R.drawable.ic_list
        } else {
            R.drawable.ic_grid
        }
        binding.ivListSwitch.setImageResource(iconResChange)
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
        binding.rvMenu.adapter = adapterMenu
        bindMenuList(isGridLayout)
        adapterMenu?.submitData(viewModel.getMenu())
    }

    private fun bindMenuList(isGridLayout: Boolean) {
        val listMode = if (isGridLayout) MenuAdapter.MODE_GRID else MenuAdapter.MODE_LIST
        adapterMenu =
            MenuAdapter(listMode = listMode, listener = object : OnItemClickedListener<Menu> {
                override fun onItemClicked(item: Menu) {
                    onItemClick(item)
                }
            })
        binding.rvMenu.apply {
            adapter = this@HomeFragment.adapterMenu
            layoutManager = GridLayoutManager(requireContext(), if (isGridLayout) 2 else 1)
        }
        adapterMenu?.submitData(viewModel.getMenu())
    }
}