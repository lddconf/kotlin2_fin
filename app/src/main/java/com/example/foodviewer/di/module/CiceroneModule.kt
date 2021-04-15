package com.example.foodviewer.di.module

import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.ui.navigation.AndroidAppScreens
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CiceroneModule {
    val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    @Provides
    fun navigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

    @Provides
    fun router(): Router = cicerone.router

    @Singleton
    @Provides
    fun screens(): IAppScreens = AndroidAppScreens()
}