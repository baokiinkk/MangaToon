package com.baokiin.mangatoon.binding

import android.os.CountDownTimer
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.ui.adapter.ItemGenreAdapter
import com.baokiin.mangatoon.ui.adapter.ItemMangaAdapter
import com.baokiin.mangatoon.ui.adapter.ItemRecommendedAdapter
import com.baokiin.mangatoon.ui.adapter.ViewPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class Binding{
    companion object {
        @BindingAdapter("android:tabLayoutId","android:adapter")
        @JvmStatic
        fun tabLayout(view: ViewPager2, tabLayout: TabLayout,adapter:ViewPageAdapter) {
            view.isUserInputEnabled = false
            view.adapter = adapter
            TabLayoutMediator(tabLayout,view, TabLayoutMediator.TabConfigurationStrategy{ tab, pos->
                when(pos){
                    0->{
                        tab.text = "Home"
                        tab.setIcon(R.drawable.ic_account)
                    }
                    1->{
                        tab.text = "search"
                    }
                }
            }).attach()
        }

        @BindingAdapter("android:tabLayoutId","android:adapter")
        @JvmStatic
        fun tabLayout2(view: ViewPager2, tabLayout: TabLayout,adapter:ItemRecommendedAdapter) {
            view.adapter = adapter
            TabLayoutMediator(tabLayout,view, TabLayoutMediator.TabConfigurationStrategy{ tab, pos->
            }).attach()
            GlobalScope.launch(Dispatchers.Main) {
                var index = 0
                while (true){
                    delay(3000)
                    if(index < adapter.itemCount-1)
                        index++
                    else
                        index = 0;
                    view.setCurrentItem(index,true)
                }

            }
        }

        @BindingAdapter("android:adapter")
        @JvmStatic
        fun recycleViewMangaAdapter(view: RecyclerView,adapter:ItemMangaAdapter) {
            view.adapter = adapter
            view.layoutManager = GridLayoutManager(view.context,1,GridLayoutManager.HORIZONTAL,false)
        }

        @BindingAdapter("android:adapter")
        @JvmStatic
        fun recycleViewGenerAdapter(view: RecyclerView,adapter:ItemGenreAdapter) {
            view.adapter = adapter
            view.layoutManager = GridLayoutManager(view.context,1,GridLayoutManager.HORIZONTAL,false)
        }

        @BindingAdapter("android:profileImage")
        @JvmStatic
        fun loadImage(view: ImageView, image: String?) {
            view.setClipToOutline(true);
            image?.let {
                view.load(it) {
                    size(700, 600)
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