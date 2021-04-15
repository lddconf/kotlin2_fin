package com.example.foodviewer.di.module

import android.widget.ImageView
import com.example.foodviewer.ui.image.GlideImageLoader
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Scheduler
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader
import javax.inject.Named
import javax.inject.Singleton

@Module
class ImageLoaderModule {
   @Singleton
    @Provides
    fun imageLoader(): IImageLoader<ImageView> =
            GlideImageLoader(true)
}