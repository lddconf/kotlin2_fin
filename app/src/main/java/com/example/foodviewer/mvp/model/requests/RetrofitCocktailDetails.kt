package com.example.foodviewer.mvp.model.requests

import com.example.foodviewer.mvp.model.api.IDataSource
import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.model.entity.toCocktailDetails
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.RuntimeException

class RetrofitCocktailDetails(
    val api: IDataSource
) : ICocktailDetails {
    override fun cocktailsByName(name: String): Single<List<CocktailDetails>> = api.searchCocktailByName(name).flatMap {
        if (it.cocktails.isEmpty()) {
            Single.error(RuntimeException("No cocktails was found"))
        } else {
            Single.fromCallable {
                it.cocktails.map { cocktailDetails ->
                    cocktailDetails.toCocktailDetails()
                }
            }
        }
    }.subscribeOn(Schedulers.io())

    override fun randomCocktail(): Single<CocktailDetails> = api.getRandomCocktail().flatMap {
        if (it.cocktails.isEmpty()) {
            Single.error(RuntimeException("Random cocktail was not generated"))
        } else {
            Single.fromCallable {
                it.cocktails.first().toCocktailDetails()
            }
        }
    }.subscribeOn(Schedulers.io())

    override fun cocktailById(id: Long): Single<CocktailDetails> =
        api.searchCocktailById(id).flatMap {
            if (it.cocktails.isEmpty()) {
                Single.error(RuntimeException("No cocktail was found"))
            } else {
                Single.fromCallable {
                    it.cocktails.first().toCocktailDetails()
                }
            }
        }.subscribeOn(Schedulers.io())

    override fun cocktailsByLetter(letter: Char): Single<List<CocktailDetails>> =
        api.listCocktailsByLetter(letter.toString()).flatMap {
            if (it.cocktails.isEmpty()) {
                Single.error(RuntimeException("No cocktails was found"))
            } else {
                Single.fromCallable {
                    it.cocktails.map { cocktailDetails ->
                        cocktailDetails.toCocktailDetails()
                    }
                }
            }
        }.subscribeOn(Schedulers.io())

    override fun cocktailsWithIngredient(name: String): Single<List<Cocktail>> =
        api.searchCocktailByIngredient(name).flatMap { cocktails->
            if (cocktails.cocktails.isEmpty()) {
                Single.error(RuntimeException("No cocktails was found"))
            } else {
                Single.fromCallable {
                    cocktails.cocktails
                }
            }
        }
}