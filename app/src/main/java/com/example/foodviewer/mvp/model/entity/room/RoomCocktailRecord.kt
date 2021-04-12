package com.example.foodviewer.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomCocktailRecord(
    @PrimaryKey val id: Long,
    val strDrink: String?,
    val strDrinkAlternative: String?,
    val strTags: String?,
    val strVideo: String?,
    val strDrinkThumb: String?,
    val strImageAttribution: String?,
    val strCreativeCommonsConfirmed: Boolean,
    val dateModified: String?,
    val strAlcoholicId: Long,
    val strGlassId: Long,
    val strCategoryId: Long,
    val strInstructions: String?
)