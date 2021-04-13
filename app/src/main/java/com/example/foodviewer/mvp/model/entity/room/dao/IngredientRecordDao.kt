package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomIngredient
import com.example.foodviewer.mvp.model.entity.room.RoomIngredientRecord
import com.example.foodviewer.mvp.model.entity.room.RoomIngredientType

@Dao
interface IngredientRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIR(ingredient: RoomIngredientRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIR(ingredients: List<RoomIngredientRecord>)

    @Update
    fun updateIR(ingredient: RoomIngredientRecord)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateIR(ingredients: List<RoomIngredientRecord>)

    @Delete
    fun deleteIR(ingredient: RoomIngredientRecord)

    @Delete
    fun deleteIR(ingredients: List<RoomIngredientRecord>)

    @Query("SELECT * from RoomIngredientRecord")
    fun getAllIR() : List<RoomIngredientRecord>

    @Query("SELECT * FROM RoomIngredientRecord WHERE idIngredient = :idIngredientRecordId LIMIT 1")
    fun findIRById(idIngredientRecordId: Long) :  RoomIngredientRecord?

    @Query("SELECT * FROM RoomIngredientRecord WHERE strIngredient = :ingredientRecordName LIMIT 1")
    fun findIRByName(ingredientRecordName: String) :  RoomIngredientRecord?
}