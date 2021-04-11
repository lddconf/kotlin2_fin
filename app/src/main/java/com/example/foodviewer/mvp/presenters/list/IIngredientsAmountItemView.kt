package com.example.foodviewer.mvp.presenters.list


interface IIngredientsAmountItemView {
    var pos: Int

    fun ingredientName(text: String)
    fun loadIngredientView(url: String)
    fun ingredientAlternatives(text: String)
    fun ingredientAmount(text: String)
    fun ingredientExists(state: Boolean)
}