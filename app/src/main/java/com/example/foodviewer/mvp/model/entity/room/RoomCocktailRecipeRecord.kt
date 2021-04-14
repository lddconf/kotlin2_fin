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
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = arrayOf(Index(value = ["ingredientName"]))
)
data class RoomCocktailRecipeRecord(
    @PrimaryKey
    val cocktailId: Long,
    val ingredientName: String,
    val recipe: String
)