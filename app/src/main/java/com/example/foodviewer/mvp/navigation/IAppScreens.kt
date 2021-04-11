package com.example.foodviewer.mvp.navigation

import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.github.terrakok.cicerone.Screen

interface IAppScreens {
    fun cocktailDetails(cocktail: Cocktail?): Screen
}