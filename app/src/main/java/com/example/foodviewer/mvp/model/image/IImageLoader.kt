package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image

interface IImageLoader<T> {
    fun load(url: String, container: T)
}