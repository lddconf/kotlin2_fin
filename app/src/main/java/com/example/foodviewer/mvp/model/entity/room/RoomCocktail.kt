package com.example.foodviewer.mvp.model.entity.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.IngredientAmount

data class RoomCocktail(
        @Embedded
        val roomCocktailRecord: RoomCocktailRecord,

        @Relation(parentColumn = "strAlcoholicId", entityColumn = "id")
        val alcoholic: RoomCocktailAlcoholic,

        @Relation(parentColumn = "strGlassId", entityColumn = "id")
        val glass: RoomCocktailGlass,

        @Relation(parentColumn = "strCategoryId", entityColumn = "id")
        val category: RoomCocktailCategory,

        @Relation(parentColumn = "id", entityColumn = "cocktailId")
        val recipe: List<RoomCocktailRecipeRecord>
)


