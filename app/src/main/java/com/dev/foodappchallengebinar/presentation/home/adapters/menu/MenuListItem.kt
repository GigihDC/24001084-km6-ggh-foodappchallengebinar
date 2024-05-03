package com.dev.foodappchallengebinar.presentation.home.adapters.menu

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.dev.foodappchallengebinar.base.ViewHolderBinder
import com.dev.foodappchallengebinar.data.models.Menu
import com.dev.foodappchallengebinar.databinding.ItemMenuListBinding
import com.dev.foodappchallengebinar.utils.toIndonesianFormat

class MenuListItem(
    private val binding: ItemMenuListBinding,
    private val listener: OnItemClickedListener<Menu>,
) : ViewHolder(binding.root), ViewHolderBinder<com.dev.foodappchallengebinar.data.models.Menu> {
    override fun bind(item: com.dev.foodappchallengebinar.data.models.Menu) {
        item.let {
            binding.ivMenuImage.load(item.imgUrl) {
                crossfade(true)
            }
            binding.tvMenuName.text = it.name
            binding.tvMenuPrice.text = it.price.toIndonesianFormat()
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}
