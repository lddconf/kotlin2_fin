package com.example.foodviewer.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = arrayOf(Index(value = ["strAlcoholic"])))
data class RoomCocktailAlcoholic(
    @PrimaryKey(autoGenerate = true)
    val id : Long,

    val strAlcoholic : String
)