package com.example.foodviewer.mvp.model.entity.cache

import com.example.foodviewer.mvp.model.api.IDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.lang.RuntimeException

/*
class RoomCacheInvalidator(val api: IDataSource,
                           val cache: IIngredientsCache) : ICacheInvalidator {
    override fun invalidateCacheIngredients(): Completable = api.getIngredients().flatMap { ingredients ->
        if (ingredients.ingredients.isEmpty()) {
            Single.error(RuntimeException("Ingredient was not found"))
        } else {
            ingredients.ingredients.forEach {
                Completable.fromCallable { api.searchIngredientByName(it.strIngredient1) }
            }
        }
    }
}
 */