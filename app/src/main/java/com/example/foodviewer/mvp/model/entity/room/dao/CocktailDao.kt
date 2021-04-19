package com.example.foodviewer.mvp.model.entity.room.dao

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.room.*
import java.util.function.Predicate

@Dao
abstract class CocktailDao : CocktailAlcoholicDao, CocktailCategoryDao, CocktailGlassDao, CocktailRecipeDao, CocktailRecordDao {
    fun insertCocktail(cocktailWithRecipe: RoomCocktailWithRecipe) {
        var alcoholicP = findCAlcoholicByName(cocktailWithRecipe.alcoholic.strAlcoholic)
        var glassP = findCGlassByName(cocktailWithRecipe.glass.strGlass)
        var categoryP = findCCategoryByName(cocktailWithRecipe.category.strCategory)

        if (alcoholicP == null) {
            insertCAlcoholic(cocktailWithRecipe.alcoholic)
            alcoholicP = findCAlcoholicByName(cocktailWithRecipe.alcoholic.strAlcoholic)
        }
        if (glassP == null) {
            insertCGlass(cocktailWithRecipe.glass)
            glassP = findCGlassByName(cocktailWithRecipe.glass.strGlass)
        }
        if (categoryP == null) {
            insertCCategory(cocktailWithRecipe.category)
            categoryP = findCCategoryByName(cocktailWithRecipe.category.strCategory)
        }

        val cWithRecipe = cocktailWithRecipe.cocktail.copy(
            strAlcoholicId = alcoholicP?.id,
            strGlassId = glassP?.id,
            strCategoryId = categoryP?.id
        )
        insertCRecord(cWithRecipe)

        val existingRecipes = findCRecipeByCocktailId(cocktailWithRecipe.cocktail.id)

        if ( existingRecipes.size > 0 ) {
            if ( existingRecipes.zip(cocktailWithRecipe.recipe).any { (old, new) ->
                    (old.ingredientName != new.ingredientName) || (old.recipe != new.recipe)
                }) { //Delete old recipes. Save new.
                deleteCRecipe(existingRecipes)
                insertCRecipe(cocktailWithRecipe.recipe)
            }
        } else {
            insertCRecipe(cocktailWithRecipe.recipe)
        }
    }

    fun insertCocktail(cocktailsWithRecipe: List<RoomCocktailWithRecipe>) = cocktailsWithRecipe.forEach {
        insertCocktail(it)
    }
/*
    fun update(cocktailWithRecipe: RoomCocktailWithRecipe) {
        var alcoholicP = findCAlcoholicByName(cocktailWithRecipe.alcoholic.strAlcoholic)
        var glassP = findCGlassByName(cocktailWithRecipe.glass.strGlass)
        var categoryP = findCCategoryByName(cocktailWithRecipe.category.strCategory)

        if (alcoholicP == null) {
            insertCAlcoholic(cocktailWithRecipe.alcoholic)
            alcoholicP = findCAlcoholicByName(cocktailWithRecipe.alcoholic.strAlcoholic)
        }
        if (glassP == null) {
            insertCGlass(cocktailWithRecipe.glass)
            glassP = findCGlassByName(cocktailWithRecipe.glass.strGlass)
        }
        if (categoryP == null) {
            insertCCategory(cocktailWithRecipe.category)
            categoryP = findCCategoryByName(cocktailWithRecipe.category.strCategory)
        }

        val existingRecipes = findCRecipeByCocktailId(cocktailWithRecipe.cocktail.id)
        if ( existingRecipes.zip(cocktailWithRecipe.recipe).all { (old, new) ->
                    old.ingredientName == new.ingredientName && old.recipe == new.recipe
                }.not() ) { //Delete old recipes. Save new.
            deleteCRecipe(existingRecipes)
            insertCRecipe(cocktailWithRecipe.recipe)
        }

        val cWithRecipe = cocktailWithRecipe.cocktail.copy(
                strAlcoholicId = alcoholicP?.id,
                strGlassId = glassP?.id,
                strCategoryId = categoryP?.id
        )
        updateCRecord(cWithRecipe)
    }
*/
    fun delete(cocktail: RoomCocktailWithRecipe) = deleteCRecord(cocktail.cocktail)

    fun delete(cocktails: List<RoomCocktailWithRecipe>) = deleteCRecord(cocktails.map { it.cocktail })

    @Transaction
    @Query("SELECT * FROM RoomCocktailRecord WHERE id = :cocktailId LIMIT 1")
    abstract fun findCocktailById(cocktailId: Long): RoomCocktailWithRecipe

    @Transaction
    @Query("SELECT * FROM RoomCocktailRecord WHERE strDrink = :cocktailName LIMIT 1")
    abstract fun findCocktailByName(cocktailName: Long): RoomCocktailWithRecipe
}