package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.json.IngredientType
import com.example.foodviewer.mvp.model.entity.room.RoomIngredient
import com.example.foodviewer.mvp.model.entity.room.RoomIngredientType

@Dao
abstract class IngredientsDao : IngredientTypeDao, IngredientRecordDao {
    fun insert(ingredient: RoomIngredient) = ingredient.apply {
        var type : RoomIngredientType?
        type = findITypeByName(ingredientType.typeName ?: "")

        if (type == null) {
            insertIType(ingredient.ingredientType)
            type = findITypeByName(ingredientType.typeName ?: "")
        }

        type?.let {
            val ingredientRecord = roomIngredientRecord.copy(strType = it.id)
            insertIR(ingredientRecord)
        }
    }

    fun insert(ingredients: List<RoomIngredient>) = ingredients.forEach {
        insert(it)
    }

    fun update(ingredient: RoomIngredient) = ingredient.apply {
        var ingredientType = ingredient.ingredientType
        ingredientType = try {
            findITypeByName(ingredientType.typeName ?: "")
        } catch (e: Throwable) {
            insertIType(ingredient.ingredientType)
            findITypeByName(ingredientType.typeName ?: "")
        }

        val ingredientRecord = roomIngredientRecord.copy(strType = ingredientType.id)
        updateIR(ingredient.roomIngredientRecord)
    }

    fun delete(ingredient: RoomIngredient) = deleteIR(ingredient.roomIngredientRecord)

    fun delete(ingredients: List<RoomIngredient>) = ingredients.map {
        it.roomIngredientRecord
    }.apply {
        deleteIR(this)
    }

    @Transaction
    @Query("SELECT * from RoomIngredientRecord")
    abstract fun getAllIngredients(): List<RoomIngredient>
/*
    @Transaction
    @Query("SELECT * FROM RoomIngredientRecord WHERE inBar > 0")
    abstract fun getAllIngredientsInBar(): List<RoomIngredient>

    @Transaction
    @Query("SELECT inBar FROM RoomIngredientRecord WHERE idIngredient = :idIngredient LIMIT 1")
    abstract fun getIngredientsInBarById(idIngredient: Long): Int
*/

    @Transaction
    @Query("SELECT * FROM RoomIngredientRecord WHERE idIngredient = :idIngredient LIMIT 1")
    abstract fun findIngredientById(idIngredient: Long): RoomIngredient

    @Transaction
    @Query("SELECT * FROM RoomIngredientRecord WHERE strIngredient = :ingredientName LIMIT 1")
    abstract fun findIngredientByName(ingredientName: Long): RoomIngredient

    @Transaction
    @Query("SELECT * FROM RoomIngredientRecord WHERE strType = :ingredientTypeId")
    abstract fun selectIngredientByTypeId(ingredientTypeId: Long): List<RoomIngredient>
}