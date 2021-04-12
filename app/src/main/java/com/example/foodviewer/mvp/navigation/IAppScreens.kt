package com.example.foodviewer.mvp.navigation

import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.github.terrakok.cicerone.Screen

interface IAppScreens {
    fun cocktailDetails(cocktailID: Long?): Screen
    fun ingredientDetails(ingredientName: String?): Screen
}