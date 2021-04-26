package com.example.foodviewer.mvp.model.entity.cache

import com.example.foodviewer.mvp.model.api.IDataSource
import com.example.foodviewer.mvp.model.entity.json.AlcoholicType
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.model.entity.json.CocktailsDetailsJSON
import com.example.foodviewer.mvp.model.entity.json.IngredientsDescription
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.schedulers.Schedulers
import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.toCocktailDetails
import java.util.concurrent.TimeUnit

class CacheInvalidator(val api: IDataSource,
                       val cocktailsCache: ICocktailsCache,
                       val ingredientsCache: IIngredientsCache) : ICacheInvalidator {

    private fun getAllIngredients(ingredients: List<String>) : Single<List<IngredientsDescription?>> {
        return if (ingredients.isEmpty()) {
            Single.error(RuntimeException("Ingredient was not found"))
        } else {
            val result = Observable.fromIterable(ingredients).delay(1, TimeUnit.MILLISECONDS).flatMap { ingredientName->
                api.searchIngredientByName(ingredientName).onErrorReturn {
                    ingredientName
                    null
                }.toObservable()
            }
            result.collectInto(mutableListOf<IngredientsDescription?>(), BiConsumer { l, i -> l.add(i) }).map { it.toList() }
        }
    }

    fun invalidateCacheIngredients(ingredients: List<String>): Completable = getAllIngredients(ingredients).map { list ->
        ingredientsCache.put(list.flatMap { it?.ingredients ?: listOf() })
    }.flatMapCompletable {
        Completable.complete()
    }.subscribeOn(Schedulers.io())

    private fun cocktailListByAlcoholic(type: AlcoholicType): Single<List<Cocktail>> =
            api.filterByAlcoholicType(type.strAlcoholic.replace(' ', '_')).flatMap { cocktails ->
                if (cocktails.cocktails.isEmpty()) {
                    Single.error(RuntimeException("No cocktails was found"))
                } else {
                    Single.fromCallable {
                        cocktails.cocktails
                    }
                }
            }.subscribeOn(Schedulers.io())

    override fun invalidateCacheCocktailsIngredients(): Completable = api.getAlcoholic().flatMap { typesList ->
        if (typesList.types.isEmpty()) {
            Single.error(RuntimeException("No cocktails was found"))
        } else {
            val request = Observable.fromIterable(typesList.types).delay(1, TimeUnit.MILLISECONDS).flatMap { alcoholicType->
                cocktailListByAlcoholic(alcoholicType).onErrorReturn { null }.toObservable()
            }
            request.collectInto(mutableListOf<Cocktail?>(), BiConsumer { l, i -> l.addAll(i) })
        }
    }.map { result -> result.filterNotNull().distinctBy { it.idDrink }.toList() }.flatMap { cocktails ->
        if (cocktails.isEmpty()) {
            Single.error(RuntimeException("Ingredient was not found"))
        } else {
            val result = Observable.fromIterable(cocktails.filterNotNull()).delay(1, TimeUnit.MILLISECONDS).flatMap { cocktail->
                api.searchCocktailByName(cocktail.strDrink).onErrorReturn { null }.toObservable()
            }
            result.collectInto(mutableListOf<CocktailsDetailsJSON?>(), BiConsumer { l, i -> l.add(i) })
        }
    }.map { list ->
        val cocktails = list.filterNotNull().flatMap { it.cocktails }.map { it.toCocktailDetails() }
        cocktailsCache.put(cocktails)
        cocktails.flatMap { it.ingredients.map { ingredient -> ingredient.name } }.distinct()
    }.flatMapCompletable { it ->
        invalidateCacheIngredients(it)
    }.subscribeOn(Schedulers.io())
}
