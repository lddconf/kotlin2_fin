package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomFavoriteCocktail
import com.example.foodviewer.mvp.model.entity.room.RoomIngredient
import com.example.foodviewer.mvp.model.entity.room.RoomIngredientInBarProp
import com.example.foodviewer.mvp.model.entity.room.RoomIngredientType

@Dao
interface FavoriteCocktailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFC(type: RoomFavoriteCocktail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFC(type: List<RoomFavoriteCocktail>)

    @Update
    fun updateFC(type: RoomFavoriteCocktail)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFC(type: List<RoomFavoriteCocktail>)

    @Delete
    fun deleteFC(type: RoomFavoriteCocktail)

    @Query("DELETE FROM RoomFavoriteCocktail WHERE cocktailId = :id")
    fun deleteFC(id : Long)

    @Query("SELECT * from RoomFavoriteCocktail")
    fun getAllFC() : List<RoomFavoriteCocktail>

    @Query("SELECT * FROM RoomFavoriteCocktail WHERE cocktailId = :id LIMIT 1")
    fun findIFCByCocktailId(id: Long) :  RoomFavoriteCocktail?
}