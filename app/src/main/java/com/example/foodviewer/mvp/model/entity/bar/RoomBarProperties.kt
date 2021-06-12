package com.example.foodviewer.mvp.model.entity.bar

import com.example.foodviewer.mvp.model.entity.room.RoomIngredientInBarProp
import com.example.foodviewer.mvp.model.entity.room.db.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.RuntimeException

class RoomBarProperties(val db: Database) : IBarProperties {
    val barChanged = PublishSubject.create<IBarProperties.IngredientInBar>()

    override fun ingredientPresentById(ingredientId: Long): Single<Boolean> = Single.fromCallable {
        db.ingredientsInBarProp.findIInBarByIngredientId(ingredientId)?.let {
            it.amount > 0
        } ?: false
    }.subscribeOn(Schedulers.io())

    override fun setupIngredientById(ingredientId: Long, exist: Boolean): Completable =
            Completable.fromCallable {
                db.ingredientsInBarProp.insertIInBarType(
                        RoomIngredientInBarProp(
                                ingredientId = ingredientId,
                                amount = if (exist) 1 else 0
                        )
                )
                val ingredientRecord = db.ingredientsDao.findIRById(ingredientId)
                ingredientRecord?.let {
                    barChanged.onNext(IBarProperties.IngredientInBar(ingredientRecord.strIngredient, exist))
                }
            }.subscribeOn(Schedulers.io())

    override fun ingredientPresentByName(ingredientName: String): Single<Boolean> =
            Single.fromCallable {
                val ingredientRecord = db.ingredientsDao.findIRByName(ingredientName)
                ingredientRecord?.let {
                    db.ingredientsInBarProp.findIInBarByIngredientId(it.idIngredient)?.let {
                        it.amount > 0
                    } ?: false
                } ?: false
            }.subscribeOn(Schedulers.io())

    override fun setupIngredientByName(ingredientName: String, exist: Boolean): Completable =
            Completable.fromCallable {
                val ingredientRecord = db.ingredientsDao.findIRByName(ingredientName)
                ingredientRecord?.let {
                    db.ingredientsInBarProp.insertIInBarType(
                            RoomIngredientInBarProp(
                                    ingredientId = it.idIngredient,
                                    amount = if (exist) 1 else 0
                            )
                    )
                    barChanged.onNext(IBarProperties.IngredientInBar(ingredientName, exist))
                } ?: throw RuntimeException("No such ingredient in bar")
            }.subscribeOn(Schedulers.io())


    override fun ingredientPresentByNames(ingredientNames: List<String>): Single<List<Boolean>> =
            Single.fromCallable {
                val list = mutableListOf<Boolean>()
                for (ingredientName in ingredientNames) {
                    val ingredientRecord = db.ingredientsDao.findIRByName(ingredientName)
                    list.add(
                            ingredientRecord?.let {
                                db.ingredientsInBarProp.findIInBarByIngredientId(it.idIngredient)?.let { ingrProp ->
                                    ingrProp.amount > 0
                                } ?: false
                            } ?: false
                    )
                }
                list.toList()
            }.subscribeOn(Schedulers.io())

    override fun ingredientInBarChangedByName(): Observable<IBarProperties.IngredientInBar> = barChanged.subscribeOn(Schedulers.io())

    override fun allIngredientsInBar(): Single<List<Long>> =
        Single.fromCallable {
            db.ingredientsInBarProp.getAllIngredientsIdInBar()
        }.subscribeOn(Schedulers.io())
}
