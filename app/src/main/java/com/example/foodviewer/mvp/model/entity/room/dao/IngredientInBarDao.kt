package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomIngredient
import com.example.foodviewer.mvp.model.entity.room.RoomIngredientInBarProp
import com.example.foodviewer.mvp.model.entity.room.RoomIngredientType

@Dao
interface IngredientInBarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIInBarType(type: RoomIngredientInBarProp)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIInBarType(type: List<RoomIngredientInBarProp>)

    @Update
    fun updateIInBarType(type: RoomIngredientInBarProp)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateIInBarType(type: List<RoomIngredientInBarProp>)

    @Delete
    fun deleteIInBarType(type: RoomIngredientInBarProp)

    @Delete
    fun deleteIInBarType(type: List<RoomIngredientInBarProp>)

    @Query("SELECT * from RoomIngredientInBarProp")
    fun getAllIInBar() : List<RoomIngredientInBarProp>

    @Query("SELECT * FROM RoomIngredientInBarProp WHERE ingredientId = :idIngredientType LIMIT 1")
    fun findIInBarByIngredientId(idIngredientType: Long) :  RoomIngredientInBarProp?
}