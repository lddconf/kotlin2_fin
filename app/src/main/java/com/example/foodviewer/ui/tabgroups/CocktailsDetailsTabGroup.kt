package com.example.foodviewer.ui.tabgroups

import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.tabgroups.ICocktailsTabGroup
import com.example.foodviewer.ui.adapter.tab.TabFragmentFactory
import com.example.foodviewer.ui.fragment.CocktailsListWithIngredientsFragment

class CocktailsDetailsTabGroup : ICocktailsTabGroup {
    override fun favoriteCocktails(favoriteCocktails: List<Cocktail>) = TabFragmentFactory("Favorites") {
        CocktailsListWithIngredientsFragment.newInstance(favoriteCocktails)
    }

    override fun allCocktails(allCocktails: List<Cocktail>)= TabFragmentFactory("All cocktails") {
        CocktailsListWithIngredientsFragment.newInstance(allCocktails)
    }

    override fun myCocktails(myCocktails: List<Cocktail>) = TabFragmentFactory("My cocktails") {
        CocktailsListWithIngredientsFragment.newInstance(myCocktails)
    }
}