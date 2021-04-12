package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecord
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailsWithRecipe
import com.example.foodviewer.mvp.model.entity.room.RoomIngredient

@Dao
abstract class CocktailDao {
    /*
    fun insert(cocktail: RoomCocktailsWithRecipe) {
        cocktail.recipe.forEach { recipeRecord->
            recipeRecord.cocktailId = cocktail.cocktail.roomCocktailRecord.id
        }

    }
*/

/*
    @Update
    abstract fun update(cocktail: RoomCocktailRecord)

    @Update
    abstract fun update(cocktail: List<RoomCocktailRecord>)

    @Delete
    abstract fun delete(cocktail: RoomCocktailRecord)

    @Delete
    abstract fun delete(cocktail: List<RoomCocktailRecord>)
*/
    @Transaction @Query("SELECT * FROM RoomCocktailRecord WHERE id = :cocktailId LIMIT 1")
    abstract fun findById(cocktailId: Long) :  RoomCocktailsWithRecipe
/*
    @Query("SELECT * FROM RoomCocktailsWithRecipe WHERE strDrink = :cocktailName LIMIT 1")
    abstract fun findByName(cocktailName: Long) :  RoomCocktailRecord
 */
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