package com.example.foodviewer.ui.image
import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader

class GlideImageLoader : IImageLoader<ImageView> {
    override fun load(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)
    }
}


