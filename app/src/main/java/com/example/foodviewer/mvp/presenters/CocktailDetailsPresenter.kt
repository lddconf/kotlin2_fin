package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.view.ICocktailDetailsView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class CocktailDetailsPresenter(
    val cocktailDetails: CocktailDetails?,
    val router: Router
) : MvpPresenter<ICocktailDetailsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        cocktailDetails?.let {
            //TODO some settings
        }
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

}