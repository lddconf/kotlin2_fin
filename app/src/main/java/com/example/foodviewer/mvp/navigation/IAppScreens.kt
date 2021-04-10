package com.example.foodviewer.mvp.navigation

import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.github.terrakok.cicerone.Screen

interface IAppScreens {
    fun cocktailDetails(cocktailDetails: CocktailDetails?): Screen
}