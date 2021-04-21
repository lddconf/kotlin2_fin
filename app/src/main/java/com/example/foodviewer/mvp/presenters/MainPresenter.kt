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

    private lateinit var  currentNavScreen: NavigationMenuItem

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IAppScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        currentNavScreen = NavigationMenuItem.COCKTAILS_MENU
        router.replaceScreen(screens.cocktailsProperties())

        viewState.initAppBar()
        viewState.initNavigationDrawer()
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
        currentNavScreen = item
    }

    fun showAbout() : Boolean {
        if ( currentNavScreen == NavigationMenuItem.ABOUT_MENU ) {
            viewState.selectMenuItem(NavigationMenuItem.ABOUT_MENU)
            return true
        }
        router.navigateTo(screens.aboutWindow())
        return true
    }

    fun showIngredients() : Boolean {
        if ( currentNavScreen == NavigationMenuItem.INGREDIENT_MENU ) {
            viewState.selectMenuItem(NavigationMenuItem.INGREDIENT_MENU)
            return true
        }

        router.replaceScreen(screens.ingredients())
        return true
    }

    fun showToCocktails() : Boolean {
        if ( currentNavScreen == NavigationMenuItem.COCKTAILS_MENU ) {
            viewState.selectMenuItem(NavigationMenuItem.COCKTAILS_MENU)
            return true
        }
        router.replaceScreen(screens.cocktailsProperties())

        return true
    }
}