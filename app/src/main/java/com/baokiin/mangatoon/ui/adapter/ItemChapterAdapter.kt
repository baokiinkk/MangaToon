package com.baokiin.mangatoon.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.mangatoon.data.model.Chapter
import com.baokiin.mangatoon.data.model.Genre
import com.baokiin.mangatoon.databinding.ItemChapterBinding


class ItemChapterAdapter(private val onClick: (Chapter, Int) -> Unit) :
    ListAdapter<Chapter, ItemChapterAdapter.ViewHolder>(
        ChapDIff()
    ) {
    class ViewHolder(private val binding: ItemChapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(
                    binding
                )
            }
        }

        fun bind(item: Chapter, onClick: ((Chapter, Int) -> Unit)? = null) {
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

    fun sort(boolean: Boolean, list: MutableList<Genre>) {
        if (boolean)
            currentList.sortBy { it.chapter_title }
        else
            currentList.sortByDescending { it.chapter_title }
    }

}

class ChapDIff : DiffUtil.ItemCallback<Chapter>() {
    // cung cấp thông tin về cách xác định phần
    override fun areItemsTheSame(
        oldItem: Chapter,
        newItem: Chapter
    ): Boolean { // cho máy biết 2 item_detail khi nào giống
        return oldItem.chapter_endpoint == newItem.chapter_endpoint // dung
    }

    override fun areContentsTheSame(
        oldItem: Chapter,
        newItem: Chapter
    ): Boolean { // cho biết item_detail khi nào cùng nội dung
        return oldItem == newItem
    }

}
