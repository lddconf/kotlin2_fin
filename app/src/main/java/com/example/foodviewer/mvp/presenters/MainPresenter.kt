package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.view.IMainActivityView
import com.example.foodviewer.ui.listeners.OnBackClickListener
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class MainPresenter(
    val router: Router,
    val screens: IAppScreens
) : MvpPresenter<IMainActivityView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val cocktail = Cocktail(
            "Margarita",
            "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
            11007
        )
        router.navigateTo(screens.cocktailDetails(cocktail.idDrink))
        val ingredientName = "Vodka"
        //router.navigateTo(screens.ingredientDetails(ingredientName))
    }

    fun backClicked() {
        router.exit()
    }
}