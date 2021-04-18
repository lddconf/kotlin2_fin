package com.example.foodviewer.mvp.tabgroups

import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.ui.adapter.tab.TabFragmentFactory

interface ICocktailsTabGroup {
    fun favoriteCocktails(favoriteCocktails : List<Cocktail>) : TabFragmentFactory
    fun allCocktails(allCocktails : List<Cocktail>) : TabFragmentFactory
    fun myCocktails(myCocktails : List<Cocktail>) : TabFragmentFactory
}