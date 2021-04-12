package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecipeRecord

@Dao
interface CocktailRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktailRecipe: RoomCocktailRecipeRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktailRecipe: List<RoomCocktailRecipeRecord>)

    @Update
    fun update(cocktailRecipe: RoomCocktailRecipeRecord)

    @Update
    fun update(cocktailRecipe: List<RoomCocktailRecipeRecord>)

    @Delete
    fun delete(cocktailRecipe: RoomCocktailRecipeRecord)

    @Delete
    fun delete(cocktailRecipe: List<RoomCocktailRecipeRecord>)

    @Query("SELECT * FROM RoomCocktailRecipeRecord WHERE cocktailId = :cocktailId LIMIT 1")
    fun findByCocktailId(cocktailId: Long) :  RoomCocktailRecipeRecord
}

/*

 @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktail: RoomCocktailsWithRecipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktail: List<RoomCocktailsWithRecipe>)

    @Update
    fun update(cocktail: RoomCocktailsWithRecipe)

    @Update
    fun update(cocktail: List<RoomCocktailsWithRecipe>)

    @Delete
    fun delete(cocktail: RoomCocktailsWithRecipe)

    @Delete
    fun delete(cocktail: List<RoomCocktailsWithRecipe>)

    @Query("SELECT * from RoomCocktailsWithRecipe")
    fun getAll() : List<RoomCocktailsWithRecipe>

    @Query("SELECT * FROM RoomCocktailsWithRecipe WHERE RoomCocktailsWithRecipe.id = :cocktailId LIMIT 1")
    fun findById(cocktailId: Long) :  RoomCocktailsWithRecipe

    @Query("SELECT * FROM RoomCocktailsWithRecipe WHERE RoomCocktailsWithRecipe.strDrink = :cocktailName LIMIT 1")
    fun findByName(cocktailName: Long) :  RoomCocktailsWithRecipe

    @Query("SELECT * FROM RoomIngredient WHERE strType = :ingredientTypeId")
    fun selectByTypeId(ingredientTypeId: Long) :  List<RoomCocktailsWithRecipe>

 */