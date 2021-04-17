package com.example.foodviewer.mvp.view

import com.example.foodviewer.mvp.model.entity.IngredientAmount
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface ICocktailWithIngredientsView : MvpView {
    @Skip
    fun displayError(description: String)

    fun initCocktailsWith()
    fun updateCocktailsWithList()
}