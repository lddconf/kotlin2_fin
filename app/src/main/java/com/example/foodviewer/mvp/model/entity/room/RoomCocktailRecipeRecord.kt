package com.example.foodviewer.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RoomCocktailRecord::class,
            parentColumns = ["id"],
            childColumns = ["cocktailId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RoomIngredientRecord::class,
            parentColumns = ["idIngredient"],
            childColumns = ["ingredientId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = arrayOf(Index(value = ["ingredientId"]))
)
class RoomCocktailRecipeRecord(
    @PrimaryKey
    val cocktailId: Long,
    val ingredientId: Long,
    val recipe: String?
)