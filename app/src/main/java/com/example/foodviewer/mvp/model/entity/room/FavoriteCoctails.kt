package com.example.foodviewer.mvp.model.entity.room

import androidx.room.*

@Entity
data class RoomFavoriteCocktail(
        @PrimaryKey
        val cocktailId: Long
)