package com.example.foodviewer.ui.tabgroups

import com.example.foodviewer.mvp.tabgroups.IIngredientsTabGroup
import com.example.foodviewer.ui.adapter.tab.TabFragmentHolder
import com.example.foodviewer.ui.fragment.IngredientsInBarListFragment
import com.example.foodviewer.ui.fragment.IngredientsListFragment

class IngredientsDetailsTabGroup : IIngredientsTabGroup {
    override fun ingredientsInBar() = TabFragmentHolder("My bar") {
        IngredientsInBarListFragment.newInstance()
    }

    override fun allIngredients(): TabFragmentHolder = TabFragmentHolder("Bar settings") {
        IngredientsListFragment.newInstance()
    }
}