package com.example.foodviewer.mvp.model.entity.json

import com.google.gson.annotations.SerializedName

data class IngredientsDescription(
    @SerializedName("ingredients")
    val ingredients: List<IngredientDetails>
)