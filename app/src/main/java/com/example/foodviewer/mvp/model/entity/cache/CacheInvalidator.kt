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

class CacheInvalidator(val api: IDataSource,
                       val cocktailsCache: ICocktailsCache,
                       val ingredientsCache: IIngredientsCache) : ICacheInvalidator {

    private fun getAllIngredients(ingredients: List<String>) : Single<List<IngredientsDescription>> = Single.fromCallable {
        if (ingredients.isEmpty()) {
            throw RuntimeException("Ingredient was not found")
        } else {
            lateinit var result: Observable<IngredientsDescription>
            ingredients.forEachIndexed { index, ingredientName ->
                result = if (index == 0) {
                    api.searchIngredientByName(ingredientName).toObservable()
                } else {
                    result.mergeWith(api.searchIngredientByName(ingredientName).toObservable())
                }
            }
            result.collectInto(mutableListOf<IngredientsDescription>(), BiConsumer { l, i -> l.add(i) })
        }
    }.map { }.subscribeOn(Schedulers.io())


    fun invalidateCacheIngredients(ingredients: List<String>): Completable = Single.fromCallable {

    }
/*
    flatMapCompletable { list ->
        list.map { it->
            ingredientsCache.put(it.flatMap { it.ingredients })
        }
        Completable.complete()
    }.subscribeOn(Schedulers.io())
*/
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
            lateinit var request: Observable<List<Cocktail>>
            typesList.types.forEachIndexed { index, alcoholicType ->
                if (index == 0) {
                    request = cocktailListByAlcoholic(alcoholicType).toObservable()
                } else {
                    request = request.mergeWith(cocktailListByAlcoholic(alcoholicType).toObservable())
                }
            }
            request.collectInto(mutableListOf<Cocktail>(), BiConsumer { l, i -> l.addAll(i) })
        }
    }.map { result -> result.distinctBy { it.idDrink }.toList() }.flatMap { cocktails ->
        if (cocktails.isEmpty()) {
            Single.error(RuntimeException("Ingredient was not found"))
        } else {
            lateinit var result: Observable<CocktailsDetailsJSON>
            cocktails.forEachIndexed { index, cocktail ->
                if (index == 0) {
                    result = api.searchCocktailByName(cocktail.strDrink).toObservable()
                } else {
                    result = result.mergeWith(api.searchCocktailByName(cocktail.strDrink).toObservable())
                }
            }
            result.collectInto(mutableListOf<CocktailsDetailsJSON>(), BiConsumer { l, i -> l.add(i) })
        }
    }.map { list ->
        val cocktails = list.flatMap { it.cocktails }.map { it.toCocktailDetails() }
        cocktailsCache.put(cocktails)
        cocktails.flatMap { it.ingredients.map { ingredient -> ingredient.name } }.distinct()
    }.flatMapCompletable { it ->
        invalidateCacheIngredients(it)
    }.subscribeOn(Schedulers.io())
}
