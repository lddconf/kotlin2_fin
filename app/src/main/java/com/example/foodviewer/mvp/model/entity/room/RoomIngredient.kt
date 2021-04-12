package com.example.foodviewer.mvp.model.entity.room

import androidx.room.*

@Entity
class RoomIngredient(
    @Embedded
    val roomIngredientRecord: RoomIngredientRecord,

    @Relation(parentColumn = "strType", entityColumn = "id")
    val ingredientType: RoomIngredientType
)