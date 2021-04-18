package com.example.foodviewer.mvp.view

import com.example.foodviewer.mvp.model.entity.IngredientAmount
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface ICocktailsDetailsTabView : MvpView {

    @Skip
    fun displayError(description: String)

    fun initTabs()
/*
    fun updateTabs()

    fun updateTab(tabId: Int)
 */
}