package com.example.foodviewer.ui.tabgroups

import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.tabgroups.ICocktailsTabGroup
import com.example.foodviewer.mvp.tabgroups.IIngredientsTabGroup
import com.example.foodviewer.ui.adapter.tab.TabFragmentHolder
import com.example.foodviewer.ui.fragment.CocktailsListWithIngredientsFragment

class IngredientsDetailsTabGroup : IIngredientsTabGroup {
    override fun ingredientsInBar() = TabFragmentHolder("My bar") {
        CocktailsListWithIngredientsFragment.newInstance(listOf<Cocktail>())
    }

    override fun allIngredients(): TabFragmentHolder = TabFragmentHolder("Bar settings") {
        CocktailsListWithIngredientsFragment.newInstance(listOf<Cocktail>())
    }
}