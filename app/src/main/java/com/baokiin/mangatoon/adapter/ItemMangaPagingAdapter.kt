package com.baokiin.mangatoon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.databinding.ItemDetailGenreBinding


class ItemMangaPagingAdapter(private val onClick: (Manga, Int) -> Unit) :
    PagingDataAdapter<Manga, ItemMangaPagingAdapter.ViewHolder>(
        MangaDIff()
    ) {
    class ViewHolder(private val binding: ItemDetailGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemDetailGenreBinding.inflate(
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
