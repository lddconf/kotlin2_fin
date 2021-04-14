package com.example.foodviewer.mvp.view

import com.example.foodviewer.mvp.model.entity.IngredientAmount
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface ICocktailDetailsView : MvpView {
    fun collapseRecipeText(collapse: Boolean = true)

    fun cocktailName(name: String)

    fun favoriteState(state: Boolean)

    fun recipeText(text: String)

    @Skip
    fun displayError(description: String)

    fun loadCocktailThumb(url: String)

    @Skip
    fun showCocktailAddedToFavoriteNotification()
    @Skip
    fun showCocktailRemovedFromFavoriteNotification()

    fun initIngredients()
    fun updateIngredientList()
}