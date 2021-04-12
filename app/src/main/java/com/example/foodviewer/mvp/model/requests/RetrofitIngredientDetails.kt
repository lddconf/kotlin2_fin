package com.example.foodviewer.mvp.model.requests

import com.example.foodviewer.mvp.model.api.IDataSource
import com.example.foodviewer.mvp.model.api.IIngredientImageUrlSource
import com.example.foodviewer.mvp.model.entity.json.IngredientDetails
import io.reactivex.rxjava3.core.Single
import java.lang.RuntimeException
import java.util.*

class RetrofitIngredientDetails(
    val api: IDataSource,
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
                    ingredients.ingredients.first()
                }
            }
        }

    override fun ingredientByName(name: String): Single<IngredientDetails> =
        api.searchIngredientByName(name).flatMap { ingredients ->
            if (ingredients.ingredients.isEmpty()) {
                Single.error(RuntimeException("Ingredient was not found"))
            } else {
                Single.fromCallable {
                    ingredients.ingredients.first()
                }
            }
        }
}