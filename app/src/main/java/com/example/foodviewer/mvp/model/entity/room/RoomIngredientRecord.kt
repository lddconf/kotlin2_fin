package com.example.foodviewer.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
class RoomIngredientRecord(
    @PrimaryKey
    val idIngredient: Long,
    val strIngredient: String,
    val strDescription: String?,
    val strType: Long,
    val strAlcohol: String?,
    val strABV: String?,
    val inBar : Int = 0
)