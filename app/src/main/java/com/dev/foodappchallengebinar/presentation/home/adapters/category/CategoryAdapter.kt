package com.dev.foodappchallengebinar.presentation.home.adapters.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dev.foodappchallengebinar.data.models.Category
import com.dev.foodappchallengebinar.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val itemClick: (Category) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val data = mutableListOf<Category>()

    fun submitData(items: List<Category>) {
        items.forEach { newItem ->
            if (!data.contains(newItem)) {
                data.add(newItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding, itemClick)

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding,
        val itemClick: (Category) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            with(item) {
                binding.tvCategoryName.text = item.name
                binding.ivCategoryImage.load(item.imgUrl) {
                    crossfade(true)
                }
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}