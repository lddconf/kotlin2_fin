package com.example.foodviewer.mvp.presenters

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
    }

    fun backClicked() {
        router.exit()
    }
}