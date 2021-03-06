package com.example.foodviewer.mvp.model.entity.bar

import com.example.foodviewer.mvp.model.entity.room.db.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

data class FavoriteState(
    val id : Long,
    val favorite: Boolean
)

interface IFavoriteCocktails {
    fun favoriteCocktailById(cocktailId: Long) : Single<Boolean>
    fun favoriteCocktailByName(cocktailName: String) : Single<Boolean>
    fun favoriteCocktailByIdSet(cocktailId: Long, favorite: Boolean) : Completable
    fun favoriteCocktailByNameSet(cocktailName: String, favorite: Boolean) : Completable
    fun allFavoriteCocktailIDs() : Single<List<Long>>

    //Notification
    fun favoriteCocktailChangedById() : Observable<FavoriteState>
}