package com.example.foodviewer.mvp.model.entity.json

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientDetails(
    val idIngredient: Long,
    val strIngredient: String,
    val strDescription: String?,
    val strType: String?,
    val strAlcohol: String?,
    val strABV: String?
) : Parcelable