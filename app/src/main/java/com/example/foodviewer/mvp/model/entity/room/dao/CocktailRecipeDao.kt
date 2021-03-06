package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecipeRecord

@Dao
interface CocktailRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCRecipe(cocktailRecipe: RoomCocktailRecipeRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCRecipe(cocktailRecipe: List<RoomCocktailRecipeRecord>)

    @Update
    fun updateCRecipe(cocktailRecipe: RoomCocktailRecipeRecord)

    @Update
    fun updateCRecipe(cocktailRecipe: List<RoomCocktailRecipeRecord>)

    @Delete
    fun deleteCRecipe(cocktailRecipe: RoomCocktailRecipeRecord)

    @Delete
    fun deleteCRecipe(cocktailRecipe: List<RoomCocktailRecipeRecord>)

    @Transaction
    @Query("SELECT * FROM RoomCocktailRecipeRecord WHERE cocktailId = :cocktailId")
    fun findCRecipeByCocktailId(cocktailId: Long) :  List<RoomCocktailRecipeRecord>

    @Transaction
    @Query("SELECT DISTINCT cocktailId FROM RoomCocktailRecipeRecord WHERE ingredientName LIKE UPPER(:ingredientName)")
    fun findCRecipeByIngredientName(ingredientName : String) : List<Long>
}