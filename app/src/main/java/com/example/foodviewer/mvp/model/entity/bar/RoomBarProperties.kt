package com.example.foodviewer.mvp.model.entity.bar

import com.example.foodviewer.mvp.model.entity.room.RoomIngredientInBarProp
import com.example.foodviewer.mvp.model.entity.room.db.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.RuntimeException

class RoomBarProperties(val db: Database) : IBarProperties {
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
                    amount = 1
                )
            )
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
                        amount = 1
                    )
                )
            } ?: throw RuntimeException("No such ingredient in bar")
        }.subscribeOn(Schedulers.io())


    override fun ingredientPresentByNames(ingredientNames: List<String>): Single<List<Boolean>> =
        Single.fromCallable {
            val list = mutableListOf<Boolean>()
            for (ingredientName in ingredientNames) {
                val ingredientRecord = db.ingredientsDao.findIRByName(ingredientName)
                list.add(
                    ingredientRecord?.let {
                        db.ingredientsInBarProp.findIInBarByIngredientId(it.idIngredient)?.let {
                            it.amount > 0
                        } ?: false
                    } ?: false
                )
            }
            list
        }
}