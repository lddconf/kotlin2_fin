package com.example.foodviewer.mvp.model.entity.json

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientDescription(
    val idIngredient: Long,
    val strIngredient: String,
    val strDescription: String,
    val strType: String,
    val strAlcohol: String? = null,
    val strABV: String? = null
) : Parcelable