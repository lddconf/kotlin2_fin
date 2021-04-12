package com.example.foodviewer.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomCocktailAlcoholic(
    @PrimaryKey(autoGenerate = true)
    val id : Long,

    val strAlcoholic : String
)