package com.example.foodviewer.ui.navigation

import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.ui.fragment.CocktailDetailsFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidAppScreens : IAppScreens {
    override fun cocktailDetails(cocktailDetails: CocktailDetails?): Screen =
        FragmentScreen {
            CocktailDetailsFragment.newInstance(cocktailDetails)
        }
}