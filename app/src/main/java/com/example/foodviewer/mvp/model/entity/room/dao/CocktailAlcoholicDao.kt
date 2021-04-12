package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailAlcoholic
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecipeRecord
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecord

@Dao
interface CocktailAlcoholicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktailRecipe: RoomCocktailAlcoholic)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktailRecipe: List<RoomCocktailAlcoholic>)

    @Update
    fun update(cocktailRecipe: RoomCocktailAlcoholic)

    @Update
    fun update(cocktailRecipe: List<RoomCocktailAlcoholic>)

    @Delete
    fun delete(cocktailRecipe: RoomCocktailAlcoholic)

    @Delete
    fun delete(cocktailRecipe: List<RoomCocktailAlcoholic>)

    @Query("SELECT * FROM RoomCocktailAlcoholic WHERE id = :alcoholicId LIMIT 1")
    fun findById(alcoholicId: Long) :  RoomCocktailAlcoholic

    @Query("SELECT * FROM RoomCocktailAlcoholic WHERE strAlcoholic = :alcoholicName LIMIT 1")
    fun findByName(alcoholicName: Long) :  RoomCocktailAlcoholic
}