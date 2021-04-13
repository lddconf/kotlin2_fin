package com.example.foodviewer.mvp.model.entity.room

import androidx.room.*
import com.example.foodviewer.mvp.model.entity.json.IngredientDetails
import com.example.foodviewer.mvp.model.entity.json.IngredientsDescription

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RoomIngredientType::class,
            parentColumns = ["id"],
            childColumns = ["strType"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = arrayOf(Index(value = ["strType"]))
)
data class RoomIngredientRecord(
    @PrimaryKey
    val idIngredient: Long,
    val strIngredient: String,
    val strDescription: String?,
    val strType: Long,
    val strAlcohol: String?,
    val strABV: String?
)

fun IngredientDetails.toRoomIngredient() = RoomIngredient(
    roomIngredientRecord = RoomIngredientRecord(
        idIngredient = idIngredient,
        strIngredient = strIngredient,
        strDescription = strDescription,
        strType = 0,
        strAlcohol = strAlcohol,
        strABV = strABV
    ),
    ingredientType = RoomIngredientType(
        id = 0,
        typeName = strType
    )
)