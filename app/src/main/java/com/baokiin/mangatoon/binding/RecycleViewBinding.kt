package com.baokiin.mangatoon.binding

import android.os.CountDownTimer
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.data.model.Genre
import com.baokiin.mangatoon.data.model.GenresList
import com.baokiin.mangatoon.ui.adapter.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class RecycleViewBinding {
    companion object {
        @BindingAdapter("android:adapter", "android:list")
        @JvmStatic
        fun editChange(
            view: SearchView,
            adapter: ItemGenreAdapter,
            list: MutableLiveData<GenresList?>
        ) {
            view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    list.value?.let {
                        adapter.filter(newText, it.list_genre)
                    }

                    return false
                }

            })
        }

        @BindingAdapter("android:adapter", "android:spanCountRecycle")
        @JvmStatic
        fun recycleViewMangaAdapter(view: RecyclerView, adapter: ItemMangaAdapter, span: Int) {
            view.adapter = adapter
            view.layoutManager = GridLayoutManager(
                view.context, span,
                if (span == 1) GridLayoutManager.HORIZONTAL
                else GridLayoutManager.VERTICAL, false
            )
        }

        @BindingAdapter("android:adapter")
        @JvmStatic
        fun recycleViewMangaPagingAdapter(view: RecyclerView, adapter: ItemMangaPagingAdapter) {
            view.adapter = adapter
            view.layoutManager = GridLayoutManager(
                view.context,
                2,
                GridLayoutManager.VERTICAL, false
            )
        }

        @BindingAdapter("android:adapter")
        @JvmStatic
        fun recycleViewGenerAdapter(view: RecyclerView, adapter: ItemGenreHomeAdapter) {
            view.adapter = adapter
            view.layoutManager =
                GridLayoutManager(view.context, 1, GridLayoutManager.HORIZONTAL, false)
        }

        @BindingAdapter("android:adapter_fragment")
        @JvmStatic
        fun recycleViewGenerFragment(view: RecyclerView, adapter: ItemGenreAdapter) {
            view.adapter = adapter
            view.layoutManager =
                GridLayoutManager(view.context, 2, GridLayoutManager.VERTICAL, false)
        }

        @BindingAdapter("android:profileImage")
        @JvmStatic
        fun loadImage(view: ImageView, image: String?) {
            view.setClipToOutline(true);
            image?.let {
                view.load(it.replace("w=225","w=500")) {
                    placeholder(R.drawable.ic_launcher_background)
                }

            }
        }
//        @BindingAdapter("android:list_item","android:adapter")
//        @JvmStatic
//        fun loadData(view: RecyclerView, data: LiveData<PagingData<Results>>, adapterPaging: ItemAdapterPaging) {
//            data.value?.let {
//                view.adapter = adapterPaging
//                view.layoutManager = GridLayoutManager(view.context,3)
//
//                GlobalScope.launch {
//                    adapterPaging.submitData(it)
//                }
//            }
//        }

    }
}