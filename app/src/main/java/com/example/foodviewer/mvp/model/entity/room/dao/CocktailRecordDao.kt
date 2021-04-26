package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecipeRecord
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecord

@Dao
interface CocktailRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCRecord(cocktailRecipe: RoomCocktailRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCRecord(cocktailRecipe: List<RoomCocktailRecord>)

    @Update
    fun updateCRecord(cocktailRecipe: RoomCocktailRecord)

    @Update
    fun updateCRecord(cocktailRecipe: List<RoomCocktailRecord>)

    @Delete
    fun deleteCRecord(cocktailRecipe: RoomCocktailRecord)

    @Delete
    fun deleteCRecord(cocktailRecipe: List<RoomCocktailRecord>)

    @Transaction
    @Query("SELECT * FROM RoomCocktailRecord WHERE id = :cocktailId LIMIT 1")
    fun findCRecordByCocktailId(cocktailId: Long) :  RoomCocktailRecord?

    @Transaction
    @Query("SELECT * FROM RoomCocktailRecord WHERE UPPER(strDrink) LIKE UPPER(:cocktailName) LIMIT 1")
    fun findCRecordByName(cocktailName: String) :  RoomCocktailRecord?


}