package com.example.foodviewer.mvp.presenters.list


interface ICocktailsWithIngredientView {
    var pos: Int
    fun cocktailName(text: String)
    fun loadCocktailView(url: String)
    fun ingredients(ingredients: List<String>, required: Boolean = false)
}