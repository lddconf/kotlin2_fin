package com.example.foodviewer.mvp.model.entity.bar

import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecord
import com.example.foodviewer.mvp.model.entity.room.RoomFavoriteCocktail
import com.example.foodviewer.mvp.model.entity.room.RoomIngredientInBarProp
import com.example.foodviewer.mvp.model.entity.room.db.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.RuntimeException

class RoomFavoriteCocktails(val db: Database) : IFavoriteCocktails {
    val favoriteChanged = PublishSubject.create<FavoriteState>()

    override fun favoriteCocktailById(cocktailId: Long): Single<Boolean> = Single.fromCallable {
        db.favoriteCocktail.findIFCByCocktailId(cocktailId)?.let {
            true
        } ?: false
    }.subscribeOn(Schedulers.io())

    override fun favoriteCocktailByName(cocktailName: String): Single<Boolean> = Single.fromCallable {
        val cocktailRecord = db.cocktailDao.findCRecordByName(cocktailName)
        cocktailRecord?.let {
            db.favoriteCocktail.findIFCByCocktailId(cocktailRecord.id)?.let { true } ?: false
        } ?: false
    }.subscribeOn(Schedulers.io())

    override fun favoriteCocktailByIdSet(cocktailId: Long, favorite: Boolean): Completable =
            Completable.fromCallable {
                if (favorite) {
                    db.favoriteCocktail.insertFC(RoomFavoriteCocktail(
                        cocktailId = cocktailId
                    ))

                    val test1 = db.favoriteCocktail.findIFCByCocktailId(cocktailId)
                    val test2 = db.favoriteCocktail.findIFCByCocktailId(cocktailId)
                } else {
                    db.favoriteCocktail.deleteFC(cocktailId)
                }
                favoriteChanged.onNext(FavoriteState(cocktailId, favorite))
            }.subscribeOn(Schedulers.io())

    override fun favoriteCocktailByNameSet(cocktailName: String, favorite: Boolean): Completable = Completable.fromCallable {
        val cocktailRecord = db.cocktailDao.findCRecordByName(cocktailName)
        cocktailRecord?.let {
            favoriteCocktailByIdSet(cocktailRecord.id, favorite)
        } ?: Completable.error(RuntimeException("No such cocktail"))
    }.subscribeOn(Schedulers.io())

    override fun allFavoriteCocktailIDs(): Single<List<Long>> = Single.fromCallable {
        db.favoriteCocktail.getAllFC().map { it.cocktailId }
    }.subscribeOn(Schedulers.io())

    override fun favoriteCocktailChangedById(): Observable<FavoriteState> = favoriteChanged.subscribeOn(Schedulers.io())
}