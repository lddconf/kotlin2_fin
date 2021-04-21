package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.view.IMainActivityView
import com.example.foodviewer.ui.listeners.OnBackClickListener
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter(
) : MvpPresenter<IMainActivityView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IAppScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initAppBar()
        viewState.initNavigationDrawer()
        router.replaceScreen(screens.cocktailsProperties())
    }

    fun restoreAppBar() {
        viewState.initAppBar()
        viewState.initNavigationDrawer()
    }


    fun backClicked() {
        router.exit()
    }
}