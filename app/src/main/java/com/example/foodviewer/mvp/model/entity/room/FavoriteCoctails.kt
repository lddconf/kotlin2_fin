package com.example.foodviewer.mvp.model.entity.room

import androidx.room.*

@Entity(
        foreignKeys = [
            ForeignKey(
                    entity = RoomCocktailRecord::class,
                    parentColumns = ["id"],
                    childColumns = ["cocktailId"],
                    onDelete = ForeignKey.CASCADE
            )
        ]
)
data class RoomFavoriteCocktail(
        @PrimaryKey
        val cocktailId: Long,
        val favorite: Boolean
)