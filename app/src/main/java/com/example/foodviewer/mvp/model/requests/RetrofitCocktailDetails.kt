package com.example.foodviewer.mvp.model.requests

import com.example.foodviewer.mvp.model.api.IDataSource
import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.cache.ICocktailsCache
import com.example.foodviewer.mvp.model.entity.json.AlcoholicType
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.model.entity.toCocktailDetails
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitCocktailDetails(
    val api: IDataSource,
    val cocktailCache: ICocktailsCache
) : ICocktailDetails {
    override fun cocktailSmallImageURLByBaseURL(url: String) = "$url/preview"

    override fun cocktailsByName(name: String): Single<List<CocktailDetails>> =
        api.searchCocktailByName(name).flatMap {
            if (it.cocktails.isEmpty()) {
                Single.error(RuntimeException("No cocktails was found"))
            } else {
                Single.fromCallable {
                    val cocktails = it.cocktails.map { cocktailDetails ->
                        cocktailDetails.toCocktailDetails()
                    }
                    cocktailCache.put(cocktails)
                    cocktails
                }
            }
        }.subscribeOn(Schedulers.io())

    override fun randomCocktail(): Single<CocktailDetails> = api.getRandomCocktail().flatMap {
        if (it.cocktails.isEmpty()) {
            Single.error(RuntimeException("Random cocktail was not generated"))
        } else {
            Single.fromCallable {
                val cocktails = it.cocktails.map { cocktailDetails ->
                    cocktailDetails.toCocktailDetails()
                }
                cocktailCache.put(cocktails)
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
                    val cocktails = it.cocktails.map { cocktailDetails ->
                        cocktailDetails.toCocktailDetails()
                    }
                    cocktailCache.put(cocktails)
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
                    val cocktails = it.cocktails.map { cocktailDetails ->
                        cocktailDetails.toCocktailDetails()
                    }
                    cocktailCache.put(cocktails)
                    cocktails
                }
            }
        }.subscribeOn(Schedulers.io())

    override fun cocktailsWithIngredient(name: String): Single<List<Cocktail>> =
        api.searchCocktailByIngredient(name).flatMap { cocktails ->
            if (cocktails.cocktails.isEmpty()) {
                Single.error(RuntimeException("No cocktails was found"))
            } else {
                Single.fromCallable {
                    cocktails.cocktails
                }
            }
        }.subscribeOn(Schedulers.io())

    override fun cocktailListByAlcoholic(type: AlcoholicType): Single<List<Cocktail>> =
        api.filterByAlcoholicType(type.strAlcoholic.replace(' ', '_')).flatMap { cocktails ->
            if (cocktails.cocktails.isEmpty()) {
                Single.error(RuntimeException("No cocktails was found"))
            } else {
                Single.fromCallable {
                    cocktails.cocktails
                }
            }
        }.subscribeOn(Schedulers.io())

    override fun cocktailsAlcoholicTypes(): Single<List<AlcoholicType>> =
        api.getAlcoholic().flatMap { typesList ->
            if (typesList.types.isEmpty()) {
                Single.error(RuntimeException("No cocktails was found"))
            } else {
                Single.fromCallable {
                    typesList.types
                }
            }
        }.subscribeOn(Schedulers.io())


    override fun getAllCocktails(): Single<List<Cocktail>> =
        api.getAlcoholic().flatMap { typesList ->
            if (typesList.types.isEmpty()) {
                Single.error(RuntimeException("No cocktails was found"))
            } else {
                lateinit var request: Observable<List<Cocktail>>
                typesList.types.forEachIndexed { index, alcoholicType ->
                    if (index == 0) {
                        request = cocktailListByAlcoholic(alcoholicType).toObservable()
                    } else {
                        request =
                            request.mergeWith(cocktailListByAlcoholic(alcoholicType).toObservable())
                    }
                }
                request.collectInto(mutableListOf<Cocktail>(), BiConsumer { l, i -> l.addAll(i) })
            }
        }.map { result -> result.distinctBy { it.idDrink }.toList() }
            .subscribeOn(Schedulers.newThread())
}