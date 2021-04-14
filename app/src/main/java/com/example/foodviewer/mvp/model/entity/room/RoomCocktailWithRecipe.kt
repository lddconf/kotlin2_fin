package com.example.foodviewer.mvp.model.entity.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.foodviewer.mvp.model.entity.CocktailDetails

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
                strDrink = strDrink,
                strDrinkAlternative = strDrinkAlternative,
                strTags = strTags,
                strVideo = strVideo,
                strDrinkThumb = strDrinkThumb,
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
                        cocktailId = id,
                        ingredientName = it.name,
                        recipe = it.amount
                )
        }
)