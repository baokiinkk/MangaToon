package com.baokiin.mangatoon.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.mangatoon.data.model.Genre
import com.baokiin.mangatoon.databinding.ItemGenreBinding
import kotlinx.android.synthetic.main.item_genre_home.view.*


class ItemGenreAdapter(private val onClick: (Genre, Int) -> Unit) :
    ListAdapter<Genre, ItemGenreAdapter.ViewHolder>(
        GenreDIff()
    ) {
    class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(
                    binding
                )
            }
        }

        fun bind(item: Genre, onClick: ((Genre, Int) -> Unit)? = null) {
            binding.data = item
            itemView.cardViewItemGenre.setCardBackgroundColor(
                when (bindingAdapterPosition % 4) {
                    0 -> Color.parseColor("#FEF1E4")
                    1 -> Color.parseColor("#E5F3EA")
                    2 -> Color.parseColor("#F4EBF7")
                    3 -> Color.parseColor("#FFF8E5")
                    else -> Color.parseColor("#EDF7FC")
                }
            )
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

    fun filter(newText: CharSequence?, list: MutableList<Genre>) {
        newText?.let {
            val words = it.split(" ").toMutableList()
            var output = ""
            for (word in words) {
                output += word.capitalize() + " "
            }
            submitList(list.filter {
                it.genre_name.contains(output.trim())
            })
            notifyDataSetChanged()
        }
    }

}
