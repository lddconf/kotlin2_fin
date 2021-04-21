package com.example.foodviewer.mvp.view

import com.example.foodviewer.R
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

enum class NavigationMenuItem(val id : Int) {
    ABOUT_MENU(0),
    INGREDIENT_MENU(1),
    COCKTAILS_MENU(2)
}

@AddToEndSingle
interface IMainActivityView : MvpView {
    fun initAppBar()
    fun initNavigationDrawer()
    fun selectMenuItem(item :NavigationMenuItem)

}