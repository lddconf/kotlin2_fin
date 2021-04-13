package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomIngredient
import com.example.foodviewer.mvp.model.entity.room.RoomIngredientType

@Dao
interface IngredientTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIType(type: RoomIngredientType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIType(type: List<RoomIngredientType>)

    @Update
    fun updateIType(type: RoomIngredientType)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateIType(type: List<RoomIngredientType>)

    @Delete
    fun deleteIType(type: RoomIngredientType)

    @Delete
    fun deleteIType(type: List<RoomIngredientType>)

    @Query("SELECT * from RoomIngredientType")
    fun getAll() : List<RoomIngredientType>

    @Query("SELECT * FROM RoomIngredientType WHERE id = :idIngredientType LIMIT 1")
    fun findITypeById(idIngredientType: Long) :  RoomIngredientType

    @Query("SELECT * FROM RoomIngredientType WHERE typeName = :ingredientType LIMIT 1")
    fun findITypeByName(ingredientType: String) :  RoomIngredientType
}