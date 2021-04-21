package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.view.*
import com.example.foodviewer.ui.listeners.OnBackClickListener
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter(
) : MvpPresenter<IMainActivityView>() {

    private var currentNavScreen = NavigationMenuItem.ABOUT_MENU

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IAppScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initAppBar()
        viewState.initNavigationDrawer()
        showToCocktails()
    }

    fun restoreAppBar() {
        viewState.initAppBar()
        viewState.initNavigationDrawer()
    }

    fun backClicked() {
        router.exit()
    }

    fun currentScreenChanged(item : NavigationMenuItem) {
        viewState.selectMenuItem(item)
    }

    fun showAbout() : Boolean {
        if ( currentNavScreen == NavigationMenuItem.ABOUT_MENU ) return true
        router.navigateTo(screens.aboutWindow())
        currentNavScreen = NavigationMenuItem.ABOUT_MENU
        return true
    }

    fun showIngredients() : Boolean {
        if ( currentNavScreen == NavigationMenuItem.INGREDIENT_MENU ) return true
        router.replaceScreen(screens.ingredients())
        currentNavScreen = NavigationMenuItem.INGREDIENT_MENU
        return true
    }

    fun showToCocktails() : Boolean {
        if ( currentNavScreen == NavigationMenuItem.COCKTAILS_MENU ) return true
        router.replaceScreen(screens.cocktailsProperties())
        currentNavScreen = NavigationMenuItem.COCKTAILS_MENU
        return true
    }
}