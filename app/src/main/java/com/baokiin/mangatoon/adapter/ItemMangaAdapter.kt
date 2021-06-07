package com.baokiin.mangatoon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.databinding.ItemMangaBinding


class ItemMangaAdapter(private val onClick: (Manga) -> Unit) :
    ListAdapter<Manga, ItemMangaAdapter.ViewHolder>(
        MangaDIff()
    ) {
    class ViewHolder(private val binding: ItemMangaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemMangaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(
                    binding
                )
            }
        }

        fun bind(item: Manga, onClick: ((Manga) -> Unit)? = null) {
            binding.data = item
            itemView.setOnClickListener {
                if (onClick != null) {
                    onClick(item)
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

class MangaDIff : DiffUtil.ItemCallback<Manga>() {
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
