package com.appturbo.cavista.utility

import android.content.Context
import android.widget.ImageView
import androidx.annotation.Nullable
import com.appturbo.cavista.repository.model.response.Image
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


class Util {


    fun loadImage(context: Context, mImgView: ImageView, @Nullable mUrl: String) {
        Glide.with(context).load(mUrl).transition(
            DrawableTransitionOptions.withCrossFade()
        ).into(mImgView)
    }


    fun getImageUrl(images: List<Image>): String {
        return if (images.size > 0) {
            images.get(0).link
        } else {
            ""
        }
    }

}