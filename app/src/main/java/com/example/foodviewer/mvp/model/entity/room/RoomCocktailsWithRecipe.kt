package com.example.foodviewer.mvp.model.entity.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

class RoomCocktailsWithRecipe(
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