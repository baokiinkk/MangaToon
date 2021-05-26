package com.baokiin.mangatoon.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.databinding.ItemRecommendedBinding


class ItemRecommendedAdapter(private val onClick: (Manga, Int) -> Unit) :
    ListAdapter<Manga, ItemRecommendedAdapter.ViewHolder>(
        FruitLocalDIff()
    ) {
    class ViewHolder(private val binding: ItemRecommendedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemRecommendedBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolder(
                    binding
                )
            }
        }

        fun bind(item: Manga, onClick: ((Manga, Int) -> Unit)? = null) {
            binding.data = item
            itemView.setOnClickListener {
                if (onClick != null) {
                    onClick(item, bindingAdapterPosition)
                }
            }
            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onClick) }
    }
}

class FruitLocalDIff : DiffUtil.ItemCallback<Manga>() {
    // cung cấp thông tin về cách xác định phần
    override fun areItemsTheSame(
        oldItem: Manga,
        newItem: Manga
    ): Boolean { // cho máy biết 2 item_detail khi nào giống
        return oldItem == newItem // dung
    }

    override fun areContentsTheSame(
        oldItem: Manga,
        newItem: Manga
    ): Boolean { // cho biết item_detail khi nào cùng nội dung
        return oldItem == newItem
    }

}
