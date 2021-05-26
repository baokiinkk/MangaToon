package com.baokiin.mangatoon.binding

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.airbnb.lottie.LottieAnimationView
import com.baokiin.mangatoon.R

class UtilsBinding{
    companion object{
        @BindingAdapter("android:profileImage")
        @JvmStatic
        fun loadImage(view: ImageView, image: String?) {
            view.setClipToOutline(true);
            image?.let {
                view.load(it.replace("w=225", "w=500")) {
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
                    size(1000,2600)
                    placeholder(R.drawable.ic_launcher_background)
                }

            }
        }

    }

}