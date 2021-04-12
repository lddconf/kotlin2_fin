package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecipeRecord
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecord

@Dao
interface CocktailRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktailRecipe: RoomCocktailRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktailRecipe: List<RoomCocktailRecord>)

    @Update
    fun update(cocktailRecipe: RoomCocktailRecord)

    @Update
    fun update(cocktailRecipe: List<RoomCocktailRecord>)

    @Delete
    fun delete(cocktailRecipe: RoomCocktailRecord)

    @Delete
    fun delete(cocktailRecipe: List<RoomCocktailRecord>)

    @Query("SELECT * FROM RoomCocktailRecord WHERE id = :cocktailId LIMIT 1")
    fun findByCocktailId(cocktailId: Long) :  RoomCocktailRecord

    @Query("SELECT * FROM RoomCocktailRecord WHERE strDrink = :cocktailName LIMIT 1")
    fun findByName(cocktailName: Long) :  RoomCocktailRecord
}