package com.example.foodviewer.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientAmount(
    val name: String,
    val amount : String,
) : Parcelable