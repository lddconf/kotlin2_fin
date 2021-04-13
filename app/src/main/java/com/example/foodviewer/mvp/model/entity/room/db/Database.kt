package com.example.foodviewer.mvp.model.entity.room.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodviewer.mvp.model.entity.room.*
import com.example.foodviewer.mvp.model.entity.room.dao.*
import java.lang.IllegalStateException

@androidx.room.Database(
    entities = [
        RoomIngredientType::class,
        RoomCocktailAlcoholic::class,
        RoomCocktailCategory::class,
        RoomCocktailGlass::class,
        RoomCocktailRecipeRecord::class,
        RoomCocktailRecord::class,
        RoomIngredientRecord::class,
        RoomIngredientInBarProp::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract val ingredientsDao: IngredientsDao
    abstract val ingredientsInBarProp: IngredientInBarDao
    /*
    abstract val cocktailAlcoholicDao: CocktailAlcoholicDao
    abstract val cocktailGlassDao: CocktailGlassDao
    abstract val cocktailCategoryDao: CocktailCategoryDao
    abstract val cocktailRecipeDao: CocktailRecipeDao
    abstract val cocktailRecordDao: CocktailRecordDao
    abstract val CocktailDao : CocktailDao
     */
    companion object {
        private const val DB_NAME = "database.db"
        private var instance: Database? = null

        fun create(context: Context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, Database::class.java, DB_NAME)
                    .fallbackToDestructiveMigration() //Clear all cache
                    .build()
            }
        }

        fun getInstance() = instance ?: throw IllegalStateException("Database not init")
    }
}