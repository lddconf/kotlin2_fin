package com.example.foodviewer.ui.navigation

import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.ui.fragment.CocktailDetailsFragment
import com.example.foodviewer.ui.fragment.IngredientDetailsFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidAppScreens : IAppScreens {
    override fun cocktailDetails(cocktail: Cocktail?): Screen =
        FragmentScreen {
            CocktailDetailsFragment.newInstance(cocktail)
        }

    override fun ingredientDetails(ingredient: String?): Screen = FragmentScreen {
        IngredientDetailsFragment.newInstance(ingredient)
    }
}