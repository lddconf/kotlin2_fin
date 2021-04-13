package com.example.foodviewer.mvp.model.entity.room

import androidx.room.*

@Entity
data class RoomIngredient(
    @Embedded
    val roomIngredientRecord: RoomIngredientRecord,

    @Relation(parentColumn = "strType", entityColumn = "id")
    val ingredientType: RoomIngredientType
)