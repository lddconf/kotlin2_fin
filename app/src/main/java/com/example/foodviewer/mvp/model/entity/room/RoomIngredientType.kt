package com.example.foodviewer.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
class RoomIngredientType(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val typeName : String?
)