package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomIngredient

@Dao
interface IngredientsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredient: RoomIngredient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredients: List<RoomIngredient>)

    @Update
    fun update(ingredient: RoomIngredient)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(ingredients: List<RoomIngredient>)

    @Delete
    fun delete(ingredient: RoomIngredient)

    @Delete
    fun delete(ingredients: List<RoomIngredient>)

    @Query("SELECT * from RoomIngredient")
    fun getAll() : List<RoomIngredient>

    @Query("SELECT * FROM RoomIngredient WHERE inBar > 0")
    fun getAllIngredientsInBar() :  List<RoomIngredient>

    @Query("SELECT * FROM RoomIngredient WHERE idIngredient = :idIngredient LIMIT 1")
    fun findById(idIngredient: Long) :  RoomIngredient

    @Query("SELECT * FROM RoomIngredient WHERE strIngredient = :ingredientName LIMIT 1")
    fun findByName(ingredientName: Long) :  RoomIngredient

    @Query("SELECT * FROM RoomIngredient WHERE strType = :ingredientTypeId")
    fun selectByTypeId(ingredientTypeId: Long) :  List<RoomIngredient>
}