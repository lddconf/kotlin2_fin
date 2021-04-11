package com.example.foodviewer.mvp.model.requests

import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.json.CocktailsDetailsJSON
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Query

interface IIngredientDetails {
    fun ingredientSmallImageURLByName(name: String): String
}