package com.example.foodviewer.mvp.view

import com.example.foodviewer.mvp.model.entity.IngredientAmount
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface IIngredientDetailsView : MvpView {
    fun collapseIngredientDescription(collapse: Boolean = true)

    fun ingredientName(name: String)

    fun inBarState(state: Boolean)

    fun ingredientDescription(text: String)

    @Skip
    fun displayError(description: String)

    @Skip
    fun showIngredientAddedNotification(ingredientName: String)
    @Skip
    fun showIngredientRemovedNotification(ingredientName: String)

    fun loadIngredientThumb(url: String)

    @Skip
    fun updateCocktailsWithList(cocktails : List<Cocktail>)

    fun initAppBar()
}