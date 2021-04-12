package com.example.foodviewer.mvp.presenters.list

import com.example.foodviewer.ui.adapter.CocktailWithIngredientRVAdapter

interface ICocktailWithIngredientListPresenter {
    var itemClickListener: ((ICocktailsWithIngredientView) -> Unit)?
    fun bindView(view: CocktailWithIngredientRVAdapter.ViewHolder)
    fun getCount(): Int
}