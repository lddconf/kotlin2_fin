package com.example.foodviewer.mvp.model.entity.room.requests

import com.example.foodviewer.mvp.model.api.IDataSource
import com.example.foodviewer.mvp.model.api.IIngredientImageUrlSource
import com.example.foodviewer.mvp.model.entity.cache.IIngredientsCache
import com.example.foodviewer.mvp.model.entity.json.IngredientDetails
import com.example.foodviewer.mvp.model.entity.json.IngredientType
import com.example.foodviewer.mvp.model.entity.room.db.Database
import com.example.foodviewer.mvp.model.requests.IIngredientDetails
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.RuntimeException

class RoomIngredientsDetailsFromCache(
        val db: Database,
        private val urlTemplates: IIngredientImageUrlSource
) : IIngredientDetails {
    override fun ingredientSmallImageURLByName(name: String) =
            urlTemplates.ingredientSmallImageURLByName(name)

    override fun ingredientMediumImageURLByName(name: String) =
            urlTemplates.ingredientMediumImageURLByName(name)

    override fun ingredientById(id: Long): Single<IngredientDetails> = Single.fromCallable {
        val ingredient = db.ingredientsDao.findIngredientById(id)
        ingredient?.let {
            IngredientDetails(idIngredient = it.roomIngredientRecord.idIngredient,
                    strIngredient = it.roomIngredientRecord.strIngredient,
                    strDescription = it.roomIngredientRecord.strDescription,
                    strType = it.ingredientType.typeName,
                    strAlcohol = it.roomIngredientRecord.strAlcohol,
                    strABV = it.roomIngredientRecord.strABV)
        }
                ?: throw RuntimeException("Ingredient was not found")
    }.subscribeOn(Schedulers.io())

    override fun ingredientByName(name: String): Single<IngredientDetails> = Single.fromCallable {
        val ingredient = db.ingredientsDao.findIngredientByName(name)
        ingredient?.let {
            IngredientDetails(idIngredient = it.roomIngredientRecord.idIngredient,
                    strIngredient = it.roomIngredientRecord.strIngredient,
                    strDescription = it.roomIngredientRecord.strDescription,
                    strType = it.ingredientType.typeName,
                    strAlcohol = it.roomIngredientRecord.strAlcohol,
                    strABV = it.roomIngredientRecord.strABV)
        }
                ?: throw RuntimeException("Ingredient was not found")
    }.subscribeOn(Schedulers.io())

    override fun allIngredients(): Single<List<IngredientType>> = Single.fromCallable {
        val ingredients = db.ingredientsDao.getAllIngredients()
        if ( ingredients.isEmpty() ) throw RuntimeException("Ingredient was not found")
        ingredients.map {
            IngredientType(strIngredient1 = it.roomIngredientRecord.strIngredient)
        }
    }.subscribeOn(Schedulers.io())
}