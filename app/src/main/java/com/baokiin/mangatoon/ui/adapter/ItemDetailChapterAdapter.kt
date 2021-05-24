package com.baokiin.mangatoon.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.mangatoon.data.model.ChapImage
import com.baokiin.mangatoon.data.model.Chapter
import com.baokiin.mangatoon.data.model.Genre
import com.baokiin.mangatoon.databinding.ItemChapterBinding
import com.baokiin.mangatoon.databinding.ItemDetailChapterBinding
import com.baokiin.mangatoon.databinding.ItemGenreBinding
import com.baokiin.mangatoon.databinding.ItemGenreHomeBinding
import kotlinx.android.synthetic.main.item_genre_home.view.*


class ItemDetailChapterAdapter(private val onClick: (ChapImage, Int) -> Unit) :
    ListAdapter<ChapImage, ItemDetailChapterAdapter.ViewHolder>(
        DetailChapDIff()
    ) {
    class ViewHolder(private val binding: ItemDetailChapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemDetailChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(
                    binding
                )
            }
        }

        fun bind(item: ChapImage, onClick: ((ChapImage, Int) -> Unit)? = null) {
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
class DetailChapDIff: DiffUtil.ItemCallback<ChapImage>() {// cung cấp thông tin về cách xác định phần
override fun areItemsTheSame(oldItem: ChapImage, newItem: ChapImage): Boolean { // cho máy biết 2 item_detail khi nào giống
    return oldItem.chapter_image_link == newItem.chapter_image_link // dung
}

    override fun areContentsTheSame(oldItem: ChapImage, newItem: ChapImage): Boolean { // cho biết item_detail khi nào cùng nội dung
        return oldItem == newItem
    }

}
