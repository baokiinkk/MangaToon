package com.baokiin.mangatoon.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.ui.adapter.ItemRecommendedAdapter
import com.baokiin.mangatoon.ui.adapter.ViewPageAdapter
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
                            tab.setIcon(R.drawable.ic_account)
                        }
                        1 -> {
                            tab.text = "Genre"
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
                            tab.setIcon(R.drawable.ic_account)
                        }
                        1 -> {
                            tab.text = "Chapter"
                            tab.setIcon(R.drawable.ic_account)
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
        @BindingAdapter("android:text_custom")
        @JvmStatic
        fun textView(view: TextView, text:String) {
           val tmp = text.split(" ")
            val tmp2 = tmp[tmp.size-2]+" "+tmp[tmp.size-1]
            view.text = tmp2
        }
    }
}