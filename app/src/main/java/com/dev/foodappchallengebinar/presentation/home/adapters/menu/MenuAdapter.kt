package com.dev.foodappchallengebinar.presentation.home.adapters.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.foodappchallengebinar.base.ViewHolderBinder
import com.dev.foodappchallengebinar.data.models.Menu
import com.dev.foodappchallengebinar.databinding.ItemMenuGridBinding
import com.dev.foodappchallengebinar.databinding.ItemMenuListBinding

class MenuAdapter(
    private val listener: OnItemClickedListener<Menu>,
    private val listMode: Int = MODE_GRID,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val MODE_LIST = 0
        const val MODE_GRID = 1
    }

    private val asyncDataDiffer =
        AsyncListDiffer<Menu>(
            this,
            object : DiffUtil.ItemCallback<Menu>() {
                override fun areItemsTheSame(
                    oldItem: Menu,
                    newItem: Menu,
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: Menu,
                    newItem: Menu,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Menu>) {
        asyncDataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (listMode == MODE_GRID) {
            val binding = ItemMenuGridBinding.inflate(inflater, parent, false)
            MenuGridItem(binding, listener)
        } else {
            val binding = ItemMenuListBinding.inflate(inflater, parent, false)
            MenuListItem(binding, listener)
        }
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        if (holder is ViewHolderBinder<*>) {
            (holder as ViewHolderBinder<Menu>).bind(asyncDataDiffer.currentList[position])
        }
    }
}

interface OnItemClickedListener<T> {
    fun onItemClicked(item: T)
}
