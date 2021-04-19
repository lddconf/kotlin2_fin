package com.example.foodviewer.mvp.tabgroups

import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.ui.adapter.tab.TabFragmentHolder

interface ICocktailsTabGroup {
    fun favoriteCocktails(favoriteCocktails : List<Cocktail>) : TabFragmentHolder
    fun allCocktails(allCocktails : List<Cocktail>) : TabFragmentHolder
    fun myCocktails(myCocktails : List<Cocktail>) : TabFragmentHolder
}