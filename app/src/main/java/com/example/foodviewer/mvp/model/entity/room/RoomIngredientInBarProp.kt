package com.example.foodviewer.mvp.model.entity.room

import androidx.room.*

@Entity
data class RoomIngredientInBarProp(
    @PrimaryKey
    val ingredientId : Long,
    val amount : Long
)