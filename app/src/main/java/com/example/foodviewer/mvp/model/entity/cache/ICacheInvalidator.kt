package com.example.foodviewer.mvp.model.entity.cache

import io.reactivex.rxjava3.core.Completable

interface ICacheInvalidator {
    //fun invalidateCacheIngredients() : Completable
    fun invalidateCacheCocktailsIngredients() : Completable
}