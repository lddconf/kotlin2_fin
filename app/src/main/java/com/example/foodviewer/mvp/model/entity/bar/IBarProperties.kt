package com.example.foodviewer.mvp.model.entity.bar

import com.example.foodviewer.mvp.model.entity.room.db.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject

interface IBarProperties {
    data class IngredientInBar(
            val name: String,
            var present: Boolean
    )

    fun ingredientPresentById(ingredientId: Long) : Single<Boolean>
    fun ingredientPresentByName(ingredientName: String) : Single<Boolean>
    fun ingredientPresentByNames(ingredientNames: List<String>) : Single<List<Boolean>>
    fun setupIngredientById(ingredientId: Long, exist : Boolean) : Completable
    fun setupIngredientByName(ingredientName: String, exist : Boolean) : Completable

    fun allIngredientsInBar() : Single<List<Long>>

    //Notification
    fun ingredientInBarChangedByName() : Observable<IngredientInBar>
}