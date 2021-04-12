package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailAlcoholic
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailGlass
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecipeRecord
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecord

@Dao
interface CocktailGlassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktailRecipe: RoomCocktailGlass)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktailRecipe: List<RoomCocktailGlass>)

    @Update
    fun update(cocktailRecipe: RoomCocktailGlass)

    @Update
    fun update(cocktailRecipe: List<RoomCocktailGlass>)

    @Delete
    fun delete(cocktailRecipe: RoomCocktailGlass)

    @Delete
    fun delete(cocktailRecipe: List<RoomCocktailGlass>)

    @Query("SELECT * FROM RoomCocktailGlass WHERE id = :glassId LIMIT 1")
    fun findById(glassId: Long) :  RoomCocktailGlass

    @Query("SELECT * FROM RoomCocktailGlass WHERE strGlass = :glassName LIMIT 1")
    fun findByName(glassName: Long) : RoomCocktailGlass
}