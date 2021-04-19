package com.example.foodviewer.mvp.presenters.tab

import com.example.foodviewer.mvp.model.entity.json.Cocktail

interface ICocktailListChangeable {
    fun cocktailList(cocktailList: List<Cocktail>)
    fun cocktailList(): List<Cocktail>?
}