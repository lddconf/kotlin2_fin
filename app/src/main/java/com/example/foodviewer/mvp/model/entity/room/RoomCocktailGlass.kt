package com.example.foodviewer.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomCocktailGlass(
    @PrimaryKey(autoGenerate = true)
    val id : Long,

    val strGlass : String
)