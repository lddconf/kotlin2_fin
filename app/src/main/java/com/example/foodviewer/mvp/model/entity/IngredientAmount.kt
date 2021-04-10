package com.example.foodviewer.mvp.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientAmount(
    val name: String,
    val amount : String
) : Parcelable