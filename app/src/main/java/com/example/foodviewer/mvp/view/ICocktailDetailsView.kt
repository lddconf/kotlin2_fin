package com.example.foodviewer.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ICocktailDetailsView : MvpView {
    fun collapseRecipeText(collapse : Boolean = true)
}