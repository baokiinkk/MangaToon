package com.baokiin.mangatoon.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.mangatoon.data.model.Genre
import com.baokiin.mangatoon.databinding.ItemGenreDescriptionBinding
import kotlinx.android.synthetic.main.item_genre_home.view.*


class ItemGenreDescriptionAdapter(private val onClick: (Genre, Int) -> Unit) :
    ListAdapter<Genre, ItemGenreDescriptionAdapter.ViewHolder>(
        GenreDIff()
    ) {
    class ViewHolder(private val binding: ItemGenreDescriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemGenreDescriptionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolder(
                    binding
                )
            }
        }

        fun bind(item: Genre, onClick: ((Genre, Int) -> Unit)? = null) {
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
