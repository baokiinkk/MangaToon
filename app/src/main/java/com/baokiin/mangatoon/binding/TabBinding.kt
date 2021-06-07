package com.baokiin.mangatoon.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.adapter.ItemMangaAdapter
import com.baokiin.mangatoon.adapter.ItemRecommendedAdapter
import com.baokiin.mangatoon.adapter.ViewPageAdapter
import com.baokiin.mangatoon.data.model.Manga
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TabBinding {
    companion object {
        @BindingAdapter("android:tabLayoutId", "android:adapter")
        @JvmStatic
        fun tabLayout(view: ViewPager2, tabLayout: TabLayout, adapter: ViewPageAdapter) {
            view.isUserInputEnabled = false
            view.adapter = adapter
            TabLayoutMediator(
                tabLayout,
                view,
                TabLayoutMediator.TabConfigurationStrategy { tab, pos ->
                    when (pos) {
                        0 -> {
                            tab.text = "Home"
                            tab.setIcon(R.drawable.ic_home)
                        }
                        1 -> {
                            tab.text = "Genre"
                            tab.setIcon(R.drawable.ic_genre)
                        }
                        2->{
                            tab.text = "Library"
                            tab.setIcon(R.drawable.ic_library)
                        }
                        3->{
                            tab.text = "Account"
                            tab.setIcon(R.drawable.ic_account)
                        }
                    }
                }).attach()
        }
        @BindingAdapter("android:tabLayoutId", "android:adapterDetail")
        @JvmStatic
        fun tabLayoutDetail(view: ViewPager2, tabLayout: TabLayout, adapter: ViewPageAdapter) {
            view.adapter = adapter
            TabLayoutMediator(
                tabLayout,
                view,
                TabLayoutMediator.TabConfigurationStrategy { tab, pos ->
                    when (pos) {
                        0 -> {
                            tab.text = "Description"
                        }
                        1 -> {
                            tab.text = "Chapters"
                        }
                    }
                }).attach()
        }
        @BindingAdapter("android:tabLayoutId", "android:adapter")
        @JvmStatic
        fun tabLayout2(view: ViewPager2, tabLayout: TabLayout, adapter: ItemRecommendedAdapter) {
            view.adapter = adapter
            TabLayoutMediator(
                tabLayout,
                view,
                TabLayoutMediator.TabConfigurationStrategy { tab, pos ->
                }).attach()
            GlobalScope.launch(Dispatchers.Main) {
                var index = 0
                while (true) {
                    delay(3000)
                    if (index < adapter.itemCount - 1)
                        index++
                    else
                        index = 0;
                    view.setCurrentItem(index, true)
                }

            }
        }
        @BindingAdapter( "android:adapter","android:dataManga")
        @JvmStatic
        fun tabLayoutLibrary(view:TabLayout, adapter: ItemMangaAdapter,data: MutableLiveData<MutableList<Manga>?>) {
            view.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabReselected(tab: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val tmp  =data.value?.filter {
                        when(tab?.position){
                            0-> it.recents == true
                            1-> it.favourite == true
                            else -> {it.recents == true}
                        }
                    }?.toMutableList()
                    adapter.submitList(tmp)
                    adapter.notifyDataSetChanged()
                }

            })
        }

    }


}