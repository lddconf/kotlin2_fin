package com.example.foodviewer.mvp.model.requests

import com.example.foodviewer.mvp.model.api.IDataSource
import com.example.foodviewer.mvp.model.api.IIngredientImageUrlSource
import com.example.foodviewer.mvp.model.entity.cache.IIngredientsCache
import com.example.foodviewer.mvp.model.entity.json.IngredientDetails
import com.example.foodviewer.mvp.model.entity.json.IngredientType
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.RuntimeException
import java.util.*

class RetrofitIngredientDetails(
    val api: IDataSource,
    val cache: IIngredientsCache,
    private val urlTemplates: IIngredientImageUrlSource
) : IIngredientDetails {
    override fun ingredientSmallImageURLByName(name: String) =
        urlTemplates.ingredientSmallImageURLByName(name)

    override fun ingredientMediumImageURLByName(name: String) =
        urlTemplates.ingredientMediumImageURLByName(name)

    override fun ingredientById(id: Long): Single<IngredientDetails> =
        api.searchIngredientById(id).flatMap { ingredients ->
            if (ingredients.ingredients.isEmpty()) {
                Single.error(RuntimeException("Ingredient was not found"))
            } else {
                Single.fromCallable {
                    cache.put(ingredients.ingredients)
                    ingredients.ingredients.first()
                }
            }
        }.subscribeOn(Schedulers.io())

    override fun ingredientByName(name: String): Single<IngredientDetails> =
        api.searchIngredientByName(name).flatMap { ingredients ->
            if (ingredients.ingredients.isEmpty()) {
                Single.error(RuntimeException("Ingredient was not found"))
            } else {
                Single.fromCallable {
                    cache.put(ingredients.ingredients)
                    ingredients.ingredients.first()
                }
            }
        }.subscribeOn(Schedulers.io())

    override fun allIngredients(): Single<List<IngredientType>> =
        api.getIngredients().flatMap { ingredients ->
            if (ingredients.ingredients.isEmpty()) {
                Single.error(RuntimeException("Ingredient was not found"))
            } else {
                Single.fromCallable {
                    ingredients.ingredients.toList()
                }
            }
        }.subscribeOn(Schedulers.io())
}