package com.baokiin.mangatoon.binding

import android.os.Handler
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.data.model.GenresList
import com.baokiin.mangatoon.ui.adapter.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

        @BindingAdapter("android:adapter","android:recyclView")
        @JvmStatic
        fun recycleViewMangaPagingAdapter(view: FloatingActionButton, adapter: ItemDetailChapterAdapter,recyclerView: RecyclerView) {
            view.setOnClickListener {
                val linearSmoothScroller: LinearSmoothScroller =
                    object : LinearSmoothScroller(view.context) {
                        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                            return 500f / displayMetrics.densityDpi
                        }
                    }
                linearSmoothScroller.targetPosition = adapter.itemCount
                recyclerView.layoutManager?.startSmoothScroll(linearSmoothScroller)
                
            }

        }

        @BindingAdapter("android:adapter_detail_chapter")
        @JvmStatic
        fun recycleViewDetailChap(view: RecyclerView, adapter: ItemDetailChapterAdapter) {
            view.adapter = adapter
            view.layoutManager = LinearLayoutManager(
                view.context,
                LinearLayoutManager.VERTICAL, false
            )

        }

        @BindingAdapter("android:adapter")
        @JvmStatic
        fun recycleViewGenerAdapter(view: RecyclerView, adapter: ItemGenreHomeAdapter) {
            view.adapter = adapter
            view.layoutManager =
                GridLayoutManager(view.context, 1, GridLayoutManager.HORIZONTAL, false)
        }
        @BindingAdapter("android:adapter")
        @JvmStatic
        fun recycleViewGenerDetailAdapter(view: RecyclerView, adapter: ItemGenreDescriptionAdapter) {
            view.adapter = adapter
            view.layoutManager =
                GridLayoutManager(view.context, 1, GridLayoutManager.HORIZONTAL, false)
        }
        @BindingAdapter("android:adapter_chapter")
        @JvmStatic
        fun recycleViewChapAdapter(view: RecyclerView, adapter: ItemChapterAdapter) {
            view.adapter = adapter
            view.layoutManager =
                GridLayoutManager(view.context, 1, GridLayoutManager.VERTICAL, false)
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

        @BindingAdapter("android:image")
        @JvmStatic
        fun loadImageChap(view: ImageView, image: String?) {
            view.setClipToOutline(true);
            image?.let {
                view.load(it) {
                    placeholder(R.drawable.ic_launcher_background)
                }

            }
        }

    }
}