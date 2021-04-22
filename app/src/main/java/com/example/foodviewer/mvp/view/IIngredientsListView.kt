package com.example.foodviewer.mvp.view

import com.example.foodviewer.mvp.model.entity.IngredientAmount
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface IIngredientsListView : MvpView {
    @Skip
    fun displayError(description: String)

    fun initIngredientsList()
    fun updateIngredientsList()

    @Skip
    fun updateIngredientInList(index: Int)
}