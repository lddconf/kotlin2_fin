package com.example.foodviewer.mvp.model.entity.cache

import com.example.foodviewer.mvp.model.entity.CocktailDetails

interface ICocktailsCache {
    fun put(cocktailsDetails: List<CocktailDetails>)
}