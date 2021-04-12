package com.example.foodviewer.mvp.model.api

interface IIngredientImageUrlSource {
    fun ingredientSmallImageURLByName(name: String): String
    fun ingredientMediumImageURLByName(name: String): String
}