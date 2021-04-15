package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.json.IngredientType
import com.example.foodviewer.mvp.model.entity.room.RoomIngredient
import com.example.foodviewer.mvp.model.entity.room.RoomIngredientType
import java.lang.RuntimeException

@Dao
abstract class IngredientsDao : IngredientTypeDao, IngredientRecordDao {
    fun insert(ingredient: RoomIngredient) = ingredient.apply {
        var type: RoomIngredientType?
        type = findITypeByName(ingredientType.typeName ?: "")

        if (type == null) {
            type = ingredient.ingredientType
            if (ingredient.ingredientType.typeName.isNullOrEmpty()) {
                type = ingredient.ingredientType.copy(typeName = "")
            }
            type.apply { insertIType(this) }
            type = findITypeByName(ingredientType.typeName ?: "")
        }

        type?.let {
            val ingredientRecord = roomIngredientRecord.copy(strType = it.id)
            insertIR(ingredientRecord)
        } ?: throw RuntimeException("Invalid insert argument")
    }

    fun insert(ingredients: List<RoomIngredient>) = ingredients.forEach {
        insert(it)
    }

    fun update(ingredient: RoomIngredient) {
        var ingredientType = findITypeByName(ingredient.ingredientType.typeName ?: "")

        if (ingredientType == null) {
            if (ingredient.ingredientType.typeName.isNullOrEmpty()) {
                ingredientType = ingredient.ingredientType.copy(typeName = "")
            }
            ingredientType.apply { insertIType(ingredient.ingredientType) }
            ingredientType = findITypeByName(ingredient.ingredientType.typeName ?: "")
        }

        ingredientType?.let {
            val ingredientRecord = ingredient.roomIngredientRecord.copy(strType = ingredientType.id)
            updateIR(ingredientRecord)
        } ?: throw RuntimeException("Invalid update argument")
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