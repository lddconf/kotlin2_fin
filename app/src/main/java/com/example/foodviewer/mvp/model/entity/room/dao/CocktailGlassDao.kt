package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailAlcoholic
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailGlass
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecipeRecord
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailRecord

@Dao
interface CocktailGlassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCGlass(cocktailRecipe: RoomCocktailGlass)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCGlass(cocktailRecipe: List<RoomCocktailGlass>)

    @Update
    fun updateCGlass(cocktailRecipe: RoomCocktailGlass)

    @Update
    fun updateCGlass(cocktailRecipe: List<RoomCocktailGlass>)

    @Delete
    fun deleteCGlass(cocktailRecipe: RoomCocktailGlass)

    @Delete
    fun deleteCGlass(cocktailRecipe: List<RoomCocktailGlass>)

    @Query("SELECT * FROM RoomCocktailGlass WHERE id = :glassId LIMIT 1")
    fun findCGlassById(glassId: Long): RoomCocktailGlass?

    @Query("SELECT * FROM RoomCocktailGlass WHERE strGlass IN(:glassName) LIMIT 1")
    fun findCGlassByName(glassName: String): RoomCocktailGlass?

    @Transaction
    fun findCGlassByNameAndInsertIfNeeded(glassName: String): RoomCocktailGlass? {
        var res = findCGlassByName(glassName)
        if (res == null) {
            insertCGlass(RoomCocktailGlass(id = 0, strGlass = glassName))
            res = findCGlassByName(glassName)
        }
        return res
    }
}