package com.baokiin.mangatoon.binding

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.BlurTransformation
import com.airbnb.lottie.LottieAnimationView
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.adapter.ItemChapterAdapter
import com.baokiin.mangatoon.data.model.Chapter
import com.google.firebase.auth.FirebaseUser
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
        @BindingAdapter("android:DetailImage")
        @JvmStatic
        fun loadDetailImage(view: ImageView, image: String?) {
            view.setClipToOutline(true);
            image?.let {
                view.load(it.replace("w=225", "w=500")) {
                    placeholder(R.drawable.templace_backround)
                    transformations(BlurTransformation(view.context,20f))
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
        @BindingAdapter("android:image_auth")
        @JvmStatic
        fun loadImageChap(view: ImageView, image: Uri?) {
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
        @BindingAdapter("android:text_custom_endpoint")
        @JvmStatic
        fun text(view: TextView, text: String?) {
            text?.let {
                val tmp = it.split("-")
                val tmp2 =tmp[tmp.size - 2] + " " + tmp[tmp.size - 1]
                view.text = tmp2.substring(0,tmp2.length-1)
            }

        }

        @BindingAdapter("android:text_auth")
        @JvmStatic
        fun textAuth(view: TextView, auth:FirebaseUser?) {
           view.text = auth?.email?:auth?.phoneNumber?:""
        }

        @BindingAdapter("android:recycleView","android:textView","android:dataChapter","android:textPos")
        @JvmStatic
        fun process(view: SeekBar, recyclerView: RecyclerView,textView: TextView,data: MutableLiveData<Chapter?>,textPos:TextView) {
            data.value?.chapter_image?.size?.let {
                view.max = it
                textView.text = it.toString()
            }
            view.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    recyclerView.smoothScrollToPosition(progress)
                    textPos.text = (progress+1).toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            })
        }
    }
}