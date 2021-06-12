package com.example.foodviewer.ui.image

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader

class GlideImageLoader(private val cornerRound: Boolean = false) : IImageLoader<ImageView> {
    override fun load(url: String, container: ImageView) {
        if (cornerRound) {
            Glide.with(container.context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(14)))
                .into(container)
        } else {
            Glide.with(container.context)
                .load(url)
                .into(container)
        }
    }
}


