package com.dev.foodappchallengebinar.presentation.home.adapters.menu

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.dev.foodappchallengebinar.base.ViewHolderBinder
import com.dev.foodappchallengebinar.data.models.Menu
import com.dev.foodappchallengebinar.databinding.ItemMenuGridBinding
import com.dev.foodappchallengebinar.utils.toIndonesianFormat

class MenuGridItem(
    private val binding: ItemMenuGridBinding,
    private val listener: OnItemClickedListener<Menu>,
) : ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
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
