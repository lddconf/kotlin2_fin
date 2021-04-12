package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomIngredient
import com.example.foodviewer.mvp.model.entity.room.RoomIngredientType

@Dao
interface IngredientTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredient: RoomIngredientType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredients: List<RoomIngredientType>)

    @Update
    fun update(ingredient: RoomIngredientType)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(ingredients: List<RoomIngredientType>)

    @Delete
    fun delete(ingredient: RoomIngredientType)

    @Delete
    fun delete(ingredients: List<RoomIngredientType>)

    @Query("SELECT * from RoomIngredientType")
    fun getAll() : List<RoomIngredientType>

    @Query("SELECT * FROM RoomIngredientType WHERE id = :idIngredientType LIMIT 1")
    fun findById(idIngredientType: Long) :  RoomIngredientType

    @Query("SELECT * FROM RoomIngredientType WHERE typeName = :ingredientType LIMIT 1")
    fun findByName(ingredientType: String) :  RoomIngredientType
}