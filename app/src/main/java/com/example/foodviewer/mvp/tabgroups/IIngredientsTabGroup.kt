package com.example.foodviewer.mvp.tabgroups

import com.example.foodviewer.ui.adapter.tab.TabFragmentHolder

interface IIngredientsTabGroup {
    fun ingredientsInBar() : TabFragmentHolder
    fun allIngredients() : TabFragmentHolder
}