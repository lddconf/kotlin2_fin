package com.example.foodviewer.mvp.presenters.list


interface IIngredientsListItemView {
    var pos: Int

    fun ingredientName(text: String)
    fun loadIngredientView(url: String)
    fun ingredientAlternatives(text: String)
    fun ingredientExists(state: Boolean)
}