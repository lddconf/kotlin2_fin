package com.example.foodviewer.mvp.model.requests

import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.model.entity.json.CocktailsDetailsJSON
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Query

interface ICocktailDetails {
    fun cocktailsByName(name: String): Single<List<CocktailDetails>>
    fun randomCocktail(): Single<CocktailDetails>
    fun cocktailById(id: Long): Single<CocktailDetails>
    fun cocktailsByLetter(letter: Char): Single<List<CocktailDetails>>
    fun cocktailsWithIngredient(name: String): Single<List<Cocktail>>
    fun cocktailSmallImageURLByBaseURL(url: String) : String
}