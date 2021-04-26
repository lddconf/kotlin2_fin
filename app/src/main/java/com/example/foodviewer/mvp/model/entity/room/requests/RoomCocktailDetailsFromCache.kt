package com.example.foodviewer.mvp.model.entity.room.requests

import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.cache.ICocktailsCache
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.model.entity.room.db.Database
import com.example.foodviewer.mvp.model.entity.room.toCocktailDetails
import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomCocktailDetailsFromCache(
    val db: Database,
    val cocktailCache: ICocktailsCache
) : ICocktailDetails {
    override fun cocktailSmallImageURLByBaseURL(url: String) = "$url/preview"

    override fun cocktailsByName(name: String): Single<List<CocktailDetails>> = Single.fromCallable {
        val cocktail = db.cocktailDao.findCocktailByName(name)
        cocktail?.let { cocktail ->
            listOf(cocktail.toCocktailDetails())
        } ?: throw RuntimeException("No cocktails was found")
    }.subscribeOn(Schedulers.io())

    override fun cocktailById(id: Long): Single<CocktailDetails> = Single.fromCallable {
        val cocktail = db.cocktailDao.findCocktailById(id)
        cocktail?.let { cocktail ->
            cocktail.toCocktailDetails()
        } ?: throw RuntimeException("No cocktails was found")
    }.subscribeOn(Schedulers.io())

    override fun cocktailsWithIngredient(name: String): Single<List<Cocktail>> = Single.fromCallable {
        val cocktailIds = db.cocktailDao.findCRecipeByIngredientName(name)
        if ( cocktailIds.isEmpty() ) throw RuntimeException("No cocktails was found")

        var cocktails = mutableListOf<Cocktail>()
        cocktailIds.forEach { id ->
            val cocktail = db.cocktailDao.findCocktailById(id)
            if (cocktail == null)  throw RuntimeException("Database integrity error")
            cocktail.let {
                cocktails.add(
                        Cocktail(it.cocktail.strDrink, it.cocktail.strDrinkThumb, it.cocktail.id)
                )
            }
        }
        cocktails.toList()
    }.subscribeOn(Schedulers.io())

/*
    override fun cocktailListByAlcoholic(type: AlcoholicType): Single<List<Cocktail>> = Single.fromCallable {
        val aType = db.cocktailDao.findCAlcoholicByName(type.strAlcoholic)
        aType?.let {
            db.cocktailDao.findCocktailById()
        }
    }


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
*/

    override fun getAllCocktails(): Single<List<Cocktail>> = Single.fromCallable {
        val cocktails = db.cocktailDao.getAllCocktails()
        cocktails.map { Cocktail(it.cocktail.strDrink, it.cocktail.strDrinkThumb, it.cocktail.id) }
    }.subscribeOn(Schedulers.io())

}