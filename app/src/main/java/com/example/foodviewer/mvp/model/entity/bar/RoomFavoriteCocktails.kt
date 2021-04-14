package com.example.foodviewer.mvp.model.entity.bar

import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecord
import com.example.foodviewer.mvp.model.entity.room.RoomFavoriteCocktail
import com.example.foodviewer.mvp.model.entity.room.RoomIngredientInBarProp
import com.example.foodviewer.mvp.model.entity.room.db.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.RuntimeException

class RoomFavoriteCocktails(val db: Database) : IFavoriteCocktails {
    override fun favoriteCocktailById(cocktailId: Long): Single<Boolean> = Single.fromCallable {
        db.favoriteCocktail.findIFCByCocktailId(cocktailId)?.favorite ?: false
    }.subscribeOn(Schedulers.io())

    override fun favoriteCocktailByName(cocktailName: String): Single<Boolean> = Single.fromCallable {
        val cocktailRecord = db.cocktailDao.findCRecordByName(cocktailName)
        cocktailRecord?.let {
            db.favoriteCocktail.findIFCByCocktailId(cocktailRecord.id)?.favorite ?: false
        } ?: false
    }.subscribeOn(Schedulers.io())

    override fun favoriteCocktailByIdSet(cocktailId: Long, favorite: Boolean): Completable =
            Completable.fromCallable {
                db.favoriteCocktail.insertFC(RoomFavoriteCocktail(
                        cocktailId = cocktailId,
                        favorite = favorite
                ))
            }.subscribeOn(Schedulers.io())

    override fun favoriteCocktailByNameSet(cocktailName: String, favorite: Boolean): Completable {
        val cocktailRecord = db.cocktailDao.findCRecordByName(cocktailName)
        return cocktailRecord?.let {
            favoriteCocktailByIdSet(cocktailRecord.id, favorite)
        } ?: Completable.error(RuntimeException("No such cocktail"))
    }
}