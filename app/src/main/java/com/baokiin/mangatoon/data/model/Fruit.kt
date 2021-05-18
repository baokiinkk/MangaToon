package com.baokiin.mangatoon.data.model
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.baokiin.mangatoon.R

import com.google.gson.annotations.SerializedName

data class Detail(val results:MutableList<Results>)
data class Results(val title:String)
data class Fruit(val data:Detail){
    companion object {
        @BindingAdapter("android:profileImage")
        @JvmStatic
        fun loadImage(view: ImageView, image: String?) {
            image?.let {
                view.load(it) {
                    size(700, 200)
                    placeholder(R.drawable.ic_launcher_background)
                }
            }
        }
        @BindingAdapter("android:adapter")
        @JvmStatic
        fun loadData(view: RecyclerView, data: MutableLiveData<MutableList<Results>?>) {
//            data.value?.let {
//                val adapter = FruitAdapter{
//                }
//                view.adapter = adapter
//                view.layoutManager = LinearLayoutManager(view.context)
//                adapter.submitList(data.value)
//            }
        }
    }
}


