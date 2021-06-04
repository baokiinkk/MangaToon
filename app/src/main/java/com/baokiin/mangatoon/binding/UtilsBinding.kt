package com.baokiin.mangatoon.binding

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.databinding.BindingAdapter
import coil.load
import com.airbnb.lottie.LottieAnimationView
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.adapter.ItemChapterAdapter
import org.w3c.dom.Text

class UtilsBinding{
    companion object {
        @BindingAdapter("android:profileImage")
        @JvmStatic
        fun loadImage(view: ImageView, image: String?) {
            view.setClipToOutline(true);
            image?.let {
                view.load(it.replace("w=225", "w=500")) {
                    placeholder(R.drawable.templace_backround)
                }

            }
        }

        @BindingAdapter("android:image")
        @JvmStatic
        fun loadImageChap(view: ImageView, image: String?) {
            view.setClipToOutline(true);
            image?.let {
                view.load(it) {
                    size(1000, 2600)
                    placeholder(R.drawable.ic_launcher_background)
                }

            }
        }

        @BindingAdapter("android:text_custom")
        @JvmStatic
        fun textView(view: TextView, text: String) {
            val tmp = text.split(" ")
            val tmp2 =tmp[0]+". "+ tmp[tmp.size - 2] + " " + tmp[tmp.size - 1]
            view.text = tmp2
        }
    }
}