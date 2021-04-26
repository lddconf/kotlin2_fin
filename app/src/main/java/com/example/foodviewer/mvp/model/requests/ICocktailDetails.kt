package com.example.foodviewer.mvp.model.requests

import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.json.AlcoholicType
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import io.reactivex.rxjava3.core.Single

interface ICocktailDetails {
    fun cocktailsByName(name: String): Single<List<CocktailDetails>>
    //fun randomCocktail(): Single<CocktailDetails>
    fun cocktailById(id: Long): Single<CocktailDetails>
    //fun cocktailsByLetter(letter: Char): Single<List<CocktailDetails>>
    fun cocktailsWithIngredient(name: String): Single<List<Cocktail>>
    fun cocktailSmallImageURLByBaseURL(url: String) : String

//    fun cocktailListByAlcoholic(type: AlcoholicType): Single<List<Cocktail>>
//    fun cocktailsAlcoholicTypes() : Single<List<AlcoholicType>>

    fun getAllCocktails(): Single<List<Cocktail>>
}