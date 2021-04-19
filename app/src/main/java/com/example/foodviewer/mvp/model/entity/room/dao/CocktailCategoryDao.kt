package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailCategory
import com.example.foodviewer.mvp.model.entity.room.RoomCocktailGlass

@Dao
interface CocktailCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCCategory(cocktailRecipe: RoomCocktailCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCCategory(cocktailRecipe: List<RoomCocktailCategory>)

    @Update
    fun updateCCategory(cocktailRecipe: RoomCocktailCategory)

    @Update
    fun updateCCategory(cocktailRecipe: List<RoomCocktailCategory>)

    @Delete
    fun deleteCCategory(cocktailRecipe: RoomCocktailCategory)

    @Delete
    fun deleteCCategory(cocktailRecipe: List<RoomCocktailCategory>)

    @Query("SELECT * FROM RoomCocktailCategory WHERE id = :categoryId LIMIT 1")
    fun findCCategoryById(categoryId: Long) :  RoomCocktailCategory?

    @Query("SELECT * FROM RoomCocktailCategory WHERE strCategory IN(:categoryName) LIMIT 1")
    fun findCCategoryByName(categoryName: String) : RoomCocktailCategory?

    @Transaction
    fun findCCategoryByNameAndInsertIfNeeded(categoryName: String): RoomCocktailCategory? {
        var res = findCCategoryByName(categoryName)
        if (res == null) {
            insertCCategory(RoomCocktailCategory(id = 0, strCategory = categoryName))
            res = findCCategoryByName(categoryName)
        }
        return res
    }
}