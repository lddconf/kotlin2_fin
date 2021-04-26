package com.example.foodviewer.mvp.model.entity.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.IngredientAmount

data class RoomCocktailWithRecipe(
        @Embedded
        val cocktail: RoomCocktailRecord,

        @Relation(parentColumn = "id", entityColumn = "cocktailId")
        val recipe: List<RoomCocktailRecipeRecord>,

        @Relation(parentColumn = "strAlcoholicId", entityColumn = "id")
        val alcoholic: RoomCocktailAlcoholic,

        @Relation(parentColumn = "strAlcoholicId", entityColumn = "id")
        val glass: RoomCocktailGlass,

        @Relation(parentColumn = "strCategoryId", entityColumn = "id")
        val category: RoomCocktailCategory
)


fun CocktailDetails.toRoomCocktailWithRecipe(): RoomCocktailWithRecipe = RoomCocktailWithRecipe(
        cocktail = RoomCocktailRecord(
                id = id,
                strDrink = strDrink ?: "",
                strDrinkAlternative = strDrinkAlternative,
                strTags = strTags,
                strVideo = strVideo,
                strDrinkThumb = strDrinkThumb,
                strInstructions = strInstructions,
                strImageAttribution = strImageAttribution,
                strCreativeCommonsConfirmed = strCreativeCommonsConfirmed,
                dateModified = dateModified,
                strAlcoholicId = 0,
                strGlassId = 0,
                strCategoryId = 0
        ),
        alcoholic = RoomCocktailAlcoholic(
                id = 0,
                strAlcoholic = strAlcoholic
        ),
        glass = RoomCocktailGlass(
                id = id,
                strGlass = strGlass
        ),
        category = RoomCocktailCategory(
                id = id,
                strCategory = strCategory
        ),

        recipe = ingredients.map {
                RoomCocktailRecipeRecord(
                        id = 0,
                        cocktailId = id,
                        ingredientName = it.name,
                        recipe = it.amount
                )
        }
)

fun RoomCocktailWithRecipe.toCocktailDetails(): CocktailDetails = CocktailDetails(
        id = this.cocktail.id,
        strDrink = this.cocktail.strDrink,
        strDrinkAlternative = this.cocktail.strDrinkAlternative,
        strTags = this.cocktail.strTags,
        strVideo = this.cocktail.strVideo,
        strCategory = this.category.strCategory,
        strAlcoholic = this.alcoholic.strAlcoholic,
        strGlass = this.glass.strGlass,
        strInstructions = this.cocktail.strInstructions,
        strDrinkThumb = this.cocktail.strDrinkThumb,
        ingredients = this.recipe.map {
                IngredientAmount(it.ingredientName, it.recipe)
        },
        strImageAttribution = this.cocktail.strImageAttribution,
        strCreativeCommonsConfirmed = this.cocktail.strCreativeCommonsConfirmed,
        dateModified = this.cocktail.dateModified
)
