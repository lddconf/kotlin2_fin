package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailCategory

@Dao
interface CocktailCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktailRecipe: RoomCocktailCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktailRecipe: List<RoomCocktailCategory>)

    @Update
    fun update(cocktailRecipe: RoomCocktailCategory)

    @Update
    fun update(cocktailRecipe: List<RoomCocktailCategory>)

    @Delete
    fun delete(cocktailRecipe: RoomCocktailCategory)

    @Delete
    fun delete(cocktailRecipe: List<RoomCocktailCategory>)

    @Query("SELECT * FROM RoomCocktailCategory WHERE id = :categoryId LIMIT 1")
    fun findById(categoryId: Long) :  RoomCocktailCategory

    @Query("SELECT * FROM RoomCocktailCategory WHERE strCategory = :categoryName LIMIT 1")
    fun findByName(categoryName: Long) : RoomCocktailCategory
}