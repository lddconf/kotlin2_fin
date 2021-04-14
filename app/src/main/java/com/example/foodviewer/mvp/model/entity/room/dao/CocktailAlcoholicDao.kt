package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailAlcoholic
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecipeRecord
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecord

@Dao
interface CocktailAlcoholicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCAlcoholic(cocktailRecipe: RoomCocktailAlcoholic)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCAlcoholic(cocktailRecipe: List<RoomCocktailAlcoholic>)

    @Update
    fun updateCAlcoholic(cocktailRecipe: RoomCocktailAlcoholic)

    @Update
    fun updateCAlcoholic(cocktailRecipe: List<RoomCocktailAlcoholic>)

    @Delete
    fun deleteCAlcoholic(cocktailRecipe: RoomCocktailAlcoholic)

    @Delete
    fun deleteCAlcoholic(cocktailRecipe: List<RoomCocktailAlcoholic>)

    @Query("SELECT * FROM RoomCocktailAlcoholic WHERE id = :alcoholicId LIMIT 1")
    fun findCAlcoholicById(alcoholicId: Long) :  RoomCocktailAlcoholic?

    @Query("SELECT * FROM RoomCocktailAlcoholic WHERE strAlcoholic = :alcoholicName LIMIT 1")
    fun findCAlcoholicByName(alcoholicName: String) :  RoomCocktailAlcoholic?
}