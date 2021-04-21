package com.example.foodviewer.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface IMainActivityView : MvpView {
    fun initAppBar()
    fun initNavigationDrawer()
}