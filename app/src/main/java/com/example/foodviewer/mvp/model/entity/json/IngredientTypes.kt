package com.example.foodviewer.mvp.model.entity.json

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientTypes(
    @SerializedName("drinks")
    val ingredients : List<IngredientType>
) : Parcelable