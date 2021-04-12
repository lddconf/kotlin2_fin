package com.example.foodviewer.mvp.model.requests

import com.example.foodviewer.mvp.model.entity.json.IngredientDetails
import io.reactivex.rxjava3.core.Single

interface IIngredientDetails {
    fun ingredientSmallImageURLByName(name: String): String
    fun ingredientMediumImageURLByName(name: String): String
    fun ingredientById(id: Long): Single<IngredientDetails>
    fun ingredientByName(name: String): Single<IngredientDetails>
}