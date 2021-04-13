package com.example.foodviewer.mvp.model.entity.cache

import com.example.foodviewer.mvp.model.entity.json.IngredientDetails

interface IIngredientsCache {
    fun put(ingredients : List<IngredientDetails>)
}